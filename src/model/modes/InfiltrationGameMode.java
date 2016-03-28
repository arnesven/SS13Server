package model.modes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.characters.OperativeCharacter;
import model.items.NuclearDisc;
import model.items.suits.JumpSuit;
import model.items.suits.OperativeSpaceSuit;
import model.items.Locator;
import model.map.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public class InfiltrationGameMode extends GameMode {

	private static final double OPERATIVE_FACTOR = 1.0/2.0;
	private Room nukieShip;
	private NuclearDisc nukieDisk;
	private List<Player> operatives = new ArrayList<>();
	private Map<Player, NPC> decoys = new HashMap<>();
	private boolean nuked = false;
	
	@Override
	protected void setUpOtherStuff(GameData gameData) {
		Locator loc = new Locator();
		loc.setTarget(nukieDisk);
		nukieShip.addItem(loc);
	}



	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
			GameData gameData) {
		nukieShip = gameData.getRoom("Nuclear Ship");
		int num = 1;
		
		List<Player> players = new ArrayList<>();
		players.addAll(gameData.getPlayersAsList());
		Collections.shuffle(players);
		for (int i = 0; i < getNoOfOperatives(gameData); ++i) {
			Player p = players.get(i);
			
			// Turn Character into decoy-npc
			NPC npc = new HumanNPC(p.getCharacter(), p.getCharacter().getStartingRoom(gameData));
			gameData.addNPC(npc);
			decoys .put(p, npc);
			GameCharacter opChar = new OperativeCharacter(num++, nukieShip.getID());
			
			p.setCharacter(opChar);
			p.takeOffSuit(); // removes default outfit
			p.putOnSuit(new JumpSuit());
			p.putOnSuit(new OperativeSpaceSuit());

			operatives.add(p);
		}
		nukieDisk = new NuclearDisc();
		gameData.getRoom("Captain's Quarters").addItem(nukieDisk);
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
		if (getGameResult(gameData) == GameOver.SHIP_NUKED) {
			return "Operatives nuked the station!<br/><img src='http://stream1.gifsoup.com/view3/1611505/nuclear-explosion-o.gif'></img>";
		}
		
		return "Operatives failed!";
	}




	public void setNuked(boolean b) {
		this.nuked  = b;
	}
	

}
