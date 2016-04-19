package model.modes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.TraitorCharacter;
import model.events.ambient.OngoingEvent;
import model.items.general.Bible;
import model.items.general.GameItem;
import model.items.general.GeigerMeter;
import model.items.general.KeyCard;
import model.items.general.PDA;
import model.items.suits.ChefsHat;
import model.items.suits.SunGlasses;
import model.map.Room;
import model.npcs.CatNPC;
import model.npcs.ChimpNPC;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import model.npcs.TARSNPC;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import model.objects.general.ElectricalMachinery;
import model.objects.consoles.GeneratorConsole;

public class TraitorGameMode extends GameMode {

	private static final double TRAITOR_FACTOR = 1.0/3.0;
	private static final int POINTS_FOR_CREW_PER_TRATIOR = 600;
	private static final int EVENT_FIX_POINTS = 20;
	private static final int POINTS_FOR_BROKEN_OBJECTS = 50;
	private List<Player> traitors = new ArrayList<>();
	private HashMap<Player, TraitorObjective> objectives = new HashMap<>();
	private String TRAITOR_START_STRING = "You are a traitor!";
	private String CREW_START_STRING = "There are traitors on the station. Find them and stop them before they ruin everything!";

    @Override
    public String getName() {
        return "Traitor";
    }

    @Override
	protected void setUpOtherStuff(GameData gameData) { 
		assignTraitors(gameData);
	}

	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
		

	}

	private void assignTraitors(GameData gameData) {
		traitors = new ArrayList<>();
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Traitor")) {
				traitors.add(p);
			}
		}
		
		// TO Few checked traitor, add som more randomly until we have
		// enough
		while (traitors.size() < getNoOfTraitors(gameData)) {
			Logger.log("Still to few traitors, adding one");
			List<Player> otherPlayers = new ArrayList<>();
			otherPlayers.addAll(gameData.getPlayersAsList());
			otherPlayers.removeAll(traitors);
			traitors.add(MyRandom.sample(otherPlayers));
		}
		
		// To Many checked traitor, removing some randomly until we have
		// just the right amount
		while (traitors.size() > getNoOfTraitors(gameData)) {
			Logger.log("Still to many traitors, removing one");
			Player notATraitor = MyRandom.sample(traitors);
			traitors.remove(notATraitor);
		}
		
		for (Player traitor : traitors) {
			TraitorObjective obj = createRandomObjective(traitor, gameData);
			traitor.setCharacter(new TraitorCharacter(traitor.getCharacter()));
			objectives.put(traitor, obj);
			traitor.addItem(new PDA(this), null);
		}
	}

	private TraitorObjective createRandomObjective(Player traitor, GameData gameData) {
		GameItem it = MyRandom.sample(stealableItems(gameData));
		Logger.log("Steal-item is " + it.getBaseName());
		for (Player p : gameData.getPlayersAsList()) {
			if (GameItem.containsItem(p.getCharacter().getStartingItems(), it)) {
				Logger.log(" " + p.getBaseName() + " has a " + it.getBaseName());
				if (p != traitor) {
					Logger.log(" match!.");
					return new LarcenyObjective(gameData, traitor, p, it);
				} else {
					Logger.log(" but he was the traitor...");
				}
			}
		}
		Logger.log("Did not find player for steal-item");

		
		double val = MyRandom.nextDouble();
		if (val < 0.5 ) {
			List<Player> targets = new ArrayList<>();
			targets.addAll(gameData.getPlayersAsList());
			targets.remove(traitor);
			if (targets.size() > 0) { 
				return new AssassinateObjective(traitor, MyRandom.sample(targets));
			} else {
				// KILL YOURSELF!
				return new AssassinateObjective(traitor, traitor);
			}
		} else  {
			List<BreakableObject> objects = SabotageObjective.getBreakableObjects(gameData);
			List<BreakableObject> sabObjects = new ArrayList<>();
			
			for (int i = 2; i > 0; ) {
				BreakableObject ob = MyRandom.sample(objects);
				if (!sabObjects.contains(ob)) {
					sabObjects.add(ob);
					i--;
				}
			}
			return new SabotageObjective(gameData, sabObjects);
		} 

			
		
		//return null;
	}

	private List<GameItem> stealableItems(GameData gameData) {
		GameItem[] stealables = new GameItem[]{new ChefsHat(), new Bible(), new SunGlasses(), new GeigerMeter(), new KeyCard()};
		List<GameItem> list = Arrays.asList(stealables);
		return list;
	}

	private int getNoOfTraitors(GameData gameData) {
		double d = gameData.getPlayersAsList().size() * TRAITOR_FACTOR;
		int traitors = Math.max(1, (int)Math.round(d));
		Logger.log("No of traitors " + traitors);
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
		result += pointsFromBrokenObjects(gameData);
		result += pointsFromFires(gameData);
		result += pointsFromBreaches(gameData);
		result += pointsFromParasites(gameData);
		result += pointsFromCat(gameData);
		result += pointsFromTARS(gameData);
		result += pointsFromChimp(gameData);
		result += pointsFromPower(gameData);
		result += pointsFromGod(gameData);
		return result;
	}



	public int pointsFromGod(GameData gameData) {
		Bible b = getBible(gameData);
		if (b == null) {
			Logger.log("Bible not found");
			return 0;
		}
		return b.getGodPoints();
	}
	
	private Bible getBible(GameData gameData) {
		for (Actor a : gameData.getActors()) {
			try {
				Bible b = (Bible)GameItem.getItem(a, new Bible());
				return b;
			} catch (NoSuchElementException nse) {
				// not found
			}
			
		}
		
		for (Room r : gameData.getRooms()) {
			for (GameItem it : r.getItems()) {
				if (it instanceof Bible) {
					Logger.log("Found bible in room " + r.getName());
					return (Bible)it;
				}
			}
		}
		return null;
	}

	public int pointsFromPower(GameData gameData) {
		if (GeneratorConsole.find(gameData).getPowerOutput() > 0.99) {
			return 200;
		}
		return 0;
	}

	public int pointsFromBrokenObjects(GameData gameData) {
		int result = 0;
		for (Room r : gameData.getRooms()) {
			for (GameObject obj : r.getObjects()) {
				if (obj instanceof ElectricalMachinery) {
					if (((ElectricalMachinery)obj).isBroken()) {
						result -= POINTS_FOR_BROKEN_OBJECTS;
					}
				}
			}
		}
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
		
		return (int)( stillAlive * getTotalPointsForCrew(gameData)
					 / shouldBeSaved.size()   );
	}

	private double getTotalPointsForCrew(GameData gameData) {
		return getNoOfTraitors(gameData)*POINTS_FOR_CREW_PER_TRATIOR;
	}

	public int pointsFromTARS(GameData gameData) {
		for (NPC npc : gameData.getNPCs()) {
			if (npc instanceof TARSNPC) {
				if (npc.isDead()) {
					return -200;
				}
			}
		}

		return 0;
	}
	
	protected int pointsFromChimp(GameData gameData) {
		for (NPC npc : gameData.getNPCs()) {
			if (npc instanceof ChimpNPC) {
				if (npc.isDead()) {
					return -50;
				}
			}
		}
		return 0;
	}

	@Override
	protected void addAntagonistStartingMessage(Player c) {
		c.addTolastTurnInfo(TRAITOR_START_STRING);
		c.addTolastTurnInfo(getObjectiveText(c));
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo(CREW_START_STRING);
		
	}

	@Override
	protected boolean isAntagonist(Player c) {
		return traitors.contains(c);
	}

	

}
