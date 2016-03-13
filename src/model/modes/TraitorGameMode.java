package model.modes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.characters.decorators.TraitorCharacter;
import model.events.OngoingEvent;
import model.items.PDA;
import model.npcs.CatNPC;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public class TraitorGameMode extends GameMode {

	private static final double TRAITOR_FACTOR = 1.0/3.0;
	private static final int POINTS_FOR_CREW = 1200;
	private static final int EVENT_FIX_POINTS = 20;
	private List<Player> traitors = new ArrayList<>();
	private HashMap<Player, TraitorObjective> objectives = new HashMap<>();
	private String TRAITOR_START_STRING = "You are a traitor!";
	private String CREW_START_STRING = "There are traitors on the station. Find them and stop them before they ruin everything!";

	@Override
	protected void setUpOtherStuff(GameData gameData) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
		assignTraitors(listOfCharacters, gameData);

	}

	private void assignTraitors(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
		traitors = new ArrayList<>();
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Traitor")) {
				traitors.add(p);
			}
		}
		
		// TO Few checked traitor, add som more randomly until we have
		// enough
		while (traitors.size() < getNoOfTraitors(gameData)) {
			System.out.println("Still to few traitors, adding one");
			List<Player> otherPlayers = new ArrayList<>();
			otherPlayers.addAll(gameData.getPlayersAsList());
			otherPlayers.removeAll(traitors);
			traitors.add(MyRandom.sample(otherPlayers));
		}
		
		// To Many checked traitor, removing some randomly until we have
		// just the right amount
		while (traitors.size() > getNoOfTraitors(gameData)) {
			System.out.println("Still to many traitors, removing one");
			Player notATraitor = MyRandom.sample(traitors);
			traitors.remove(notATraitor);
		}
		
		for (Player traitor : traitors) {
			TraitorObjective obj = createRandomObjective(traitor, gameData);
			traitor.setCharacter(new TraitorCharacter(traitor.getCharacter()));
			objectives.put(traitor, obj);
			traitor.addItem(new PDA(this));
		}
	}

	private TraitorObjective createRandomObjective(Player traitor, GameData gameData) {
		int val = MyRandom.nextInt(1);
		if (val == 0) {
			List<Player> targets = new ArrayList<>();
			targets.addAll(gameData.getPlayersAsList());
			targets.remove(traitor);
			if (targets.size() > 0) { 
				return new AssassinateObjective(traitor, MyRandom.sample(targets));
			} else {
				return new AssassinateObjective(traitor, traitor);
			}
		}
		
		return null;
	}

	private int getNoOfTraitors(GameData gameData) {
		double d = gameData.getPlayersAsList().size() * TRAITOR_FACTOR;
		int traitors = Math.max(1, (int)Math.round(d));
		System.out.println("No of traitors " + traitors);
		return traitors;
	}

	protected GameOver getGameResult(GameData gameData) {
		if (gameData.isAllDead()) {
			return GameOver.ALL_DEAD;
		} else if (gameData.getRound() == gameData.getNoOfRounds()) {
			return GameOver.TIME_IS_UP;
		} else if (allGoodGuysDead(gameData)) {
			return GameOver.PROTAGONISTS_DEAD;
		}
		return null;
	}
	
	private boolean allGoodGuysDead(GameData gameData) {
		for (Player p : gameData.getPlayersAsList()) {
			if (!traitors.contains(p)) {
				if (!p.isDead()) {
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean gameOver(GameData gameData) {
		return getGameResult(gameData) != null;
	}

	

	@Override
	public void setStartingLastTurnInfo() {
		for (Player traitor : traitors) {
			traitor.addTolastTurnInfo(getObjectiveText(traitor));
		}
	}

	private String getObjectiveText(Player traitor) {
		return "Objective; \"" + objectives.get(traitor).getText() + "\"";
	}

	@Override
	protected void addStartingMessages(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			if (traitors.contains(c)) {
				c.addTolastTurnInfo(TRAITOR_START_STRING );
				c.addTolastTurnInfo(getObjectiveText(c));
			} else {
				c.addTolastTurnInfo(CREW_START_STRING);
			}
		}
	}
	
	@Override
	protected void triggerModeSpecificEvents(GameData gameData) {
		for (TraitorObjective obj : objectives.values()) {
			obj.isCompleted(gameData);
		}
	}

	@Override
	public String getSummary(GameData gameData) {
		return (new TraitorModeStats(gameData, this)).toString();
	}

	public List<Player> getTraitors() {
		return traitors;
	}
	
	public Map<Player, TraitorObjective> getObjectives() {
		return objectives;
	}

	public int getScore(GameData gameData) {
		int result = 0;
		
		result += pointsFromObjectives(gameData);
		result += pointsFromSavedCrew(gameData);
		result += pointsFromFires(gameData);
		result += pointsFromBreaches(gameData);
		result += pointsFromParasites(gameData);
		result += pointsFromCat(gameData);
		return result;
	}

	public int pointsFromParasites(GameData gameData) {
		int res = 0;
		for (NPC npc : allParasites) {
			if (npc.isDead()) {
				res += 5;
			}
		}
		
		return res;
	}

	public int pointsFromCat(GameData gameData) {
		for (NPC npc : gameData.getNPCs()) {
			if (npc instanceof CatNPC) {
				if (npc.isDead()) {
					return -50;
				}
			}
		}
		return 0;
	}

	public int pointsFromBreaches(GameData gameData) {
		OngoingEvent breaches = (OngoingEvent) this.getEvents().get("hull breaches");
		return (breaches.noOfFixed() - breaches.noOfOngoing()) * EVENT_FIX_POINTS;
	}

	public int pointsFromFires(GameData gameData) {
		OngoingEvent fire = (OngoingEvent) this.getEvents().get("fires");
		return (fire.noOfFixed() - fire.noOfOngoing()) * EVENT_FIX_POINTS;
	}

	public int pointsFromObjectives(GameData gameData) {
		int result = 0;
		for (TraitorObjective ob : objectives.values()) {
			if (ob.wasCompleted()) {
				result -= ob.getPoints();
			}
		}
		return result;
	}

	public int pointsFromSavedCrew(GameData gameData) {
		List<Actor> shouldBeSaved = new ArrayList<>(gameData.getActors());
		shouldBeSaved.removeAll(traitors);
		for (Iterator<Actor> it = shouldBeSaved.iterator(); it.hasNext() ; ) {
			Actor a = it.next();
			if (!(a instanceof Player || a instanceof HumanNPC)) {
				it.remove();
			}
		}
		int stillAlive = 0;
		for (Actor act : shouldBeSaved) {
			if (!act.isDead()) {
				stillAlive++;
			}
		}
		
		return (int)(stillAlive * ((double)POINTS_FOR_CREW)/shouldBeSaved.size());
	}

	

}
