package model.modes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.characters.OperativeCharacter;
import model.characters.crew.CaptainCharacter;
import model.events.Event;
import model.events.NoPressureEvent;
import model.items.GameItem;
import model.items.NuclearDisc;
import model.items.suits.JumpSuit;
import model.items.suits.OperativeSpaceSuit;
import model.items.Locator;
import model.map.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import model.objects.PressurePanel;
import model.items.weapons.Revolver;
import model.items.NuclearDisc;

public class InfiltrationGameMode extends GameMode {

	private static final double OPERATIVE_FACTOR = 1.0/2.0;
	private Room nukieShip;
	private NuclearDisc nukieDisk;
	private List<Player> operatives = new ArrayList<>();
	private Map<Player, NPC> decoys = new HashMap<>();
	private boolean nuked = false;
	
	@Override
	protected void setUpOtherStuff(GameData gameData) {
		for (Actor a : gameData.getActors()) {
			for (GameItem it : a.getItems()) {
				if (it instanceof NuclearDisc) {
					nukieDisk = (NuclearDisc)it;
					break;
				}
			}
		}
		Locator loc = new Locator();
		loc.setTarget(nukieDisk);
		
		if (operatives.size() > 0) {
			MyRandom.sample(operatives).addItem(loc, null);
		}
		
		nukieShip.addObject(new NuclearBomb(nukieShip));
		Event e = new NoPressureEverEvent(nukieShip);
		gameData.addEvent(e);
		nukieShip.addEvent(e);
		
	}



	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
			GameData gameData) {
		nukieShip = gameData.getRoom("Nuclear Ship");
		int num = 1;
		
		List<Player> opPlayers = new ArrayList<>();
		
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Operative") && 
					!(p.getCharacter() instanceof CaptainCharacter)) {
				opPlayers.add(p);
			}
		}
		
		while (opPlayers.size() > getNoOfOperatives(gameData)) {
			// Too many operatives, remove some.
			opPlayers.remove(MyRandom.sample(opPlayers));
		}
	
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.addAll(gameData.getPlayersAsList());
		while (opPlayers.size() < getNoOfOperatives(gameData)) {
			// Too few checked operatives add some.
			Player p = MyRandom.sample(allPlayers);
			if (!opPlayers.contains(p)) {
				opPlayers.add(p);
			}
		}
		

		for (int i = 0; i < getNoOfOperatives(gameData); ++i) {
			Player p = opPlayers.get(i);
			
			// Turn Character into decoy-npc
			p.getCharacter().setClient(null);
			NPC npc = new HumanNPC(p.getCharacter(), p.getCharacter().getStartingRoom(gameData));
			gameData.addNPC(npc);
			decoys.put(p, npc);
			GameCharacter opChar = new OperativeCharacter(num++, nukieShip.getID());
			
			p.setCharacter(opChar);
			p.takeOffSuit(); // removes default outfit
			p.putOnSuit(new JumpSuit());
			p.putOnSuit(new OperativeSpaceSuit());

			operatives.add(p);
		}
	}

	private int getNoOfOperatives(GameData gameData) {
		return (int)Math.floor(gameData.getPlayersAsList().size() * OPERATIVE_FACTOR);
	}

	private boolean allOpsDead(GameData gameData) {
		boolean allOpsDead = true;
		for (Player p : operatives) {
			if (!p.isDead()) {
				allOpsDead = false;
			}
		}
		return allOpsDead;
	}
	
	protected GameOver getGameResult(GameData gameData) {
		if (gameData.isAllDead()) {
			return GameOver.ALL_DEAD;
		} else if (gameData.getRound() == gameData.getNoOfRounds()) {
			return GameOver.TIME_IS_UP;
		} else if (allOpsDead(gameData)) {
			return GameOver.ANTAGONISTS_DEAD;
		} else if (nuked) {
			return GameOver.SHIP_NUKED;
		}
		return null;
	}
	
	@Override
	public boolean gameOver(GameData gameData) {
		return getGameResult(gameData) != null;
	}

	

	@Override
	public void setStartingLastTurnInfo() {
		for (Player p : operatives) {
			p.addTolastTurnInfo("Your decoy is " + decoys.get(p).getBaseName() + 
							" (in " + decoys.get(p).getPosition().getName() + ")");
		}

	}

	@Override
	protected void addAntagonistStartingMessage(Player c) {
		StringBuffer decoyStr = new StringBuffer();
		
		for (NPC npc : decoys.values()) {
			if (npc != decoys.get(c)) {
				decoyStr.append(npc.getBaseName() + ", ");
			}
		}
		
		c.addTolastTurnInfo("You are a nuclear operative! " + 
							"Infiltrate the station and find the nuclear disk. " + 
							"Then leave the station through an airlock. " + 
							"You can pretend to be the " + decoys.get(c).getBaseName() + 
							" (in " + decoys.get(c).getPosition().getName() + ")");
		if (!decoyStr.toString().equals("")) {
			c.addTolastTurnInfo("The other decoys are " + decoyStr.toString());
		}
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo("Nuclear operatives are infiltrating the station. Prevent them from getting the nuclear disk. If they leave the station with it, they will nuke the station!");
	}

	@Override
	protected boolean isAntagonist(Player c) {
		return operatives.contains(c);
	}
	
	@Override
	public String getSummary(GameData gameData) {
		return (new InfiltrationModeStats(gameData, this)).toString();
	}


	public void setNuked(boolean b) {
		this.nuked  = b;
	}
	
	public boolean isNuked() {
		return this.nuked;
	}


	public boolean isOperative(Actor value) {
		if (value instanceof Player) {
			if (operatives.contains((Player)value)) {
				return true;
			}
		}
		return false;
	}



	public NPC getDecoy(Actor value) {
		if (value instanceof Player) {
			return decoys.get((Player)value);
		}
		return null;
	}



	
	

}
