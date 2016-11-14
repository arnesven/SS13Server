package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.characters.general.AICharacter;
import model.objects.consoles.AIConsole;
import model.objects.general.NuclearBomb;
import util.HTMLFont;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.general.OperativeCharacter;
import model.characters.crew.CaptainCharacter;
import model.events.Event;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.items.suits.JumpSuit;
import model.items.suits.OperativeSpaceSuit;
import model.items.general.Locator;
import model.map.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public class OperativesGameMode extends GameMode {

	private static final double OPERATIVE_FACTOR = 1.0/2.0;
	private Room nukieShip;
	private NuclearDisc nukieDisk;
	private List<Player> operatives = new ArrayList<>();
	private Map<Player, NPC> decoys = new HashMap<>();
	private boolean nuked = false;

    @Override
    public String getName() {
        return "Operatives";
    }

    @Override
	protected void setUpOtherStuff(GameData gameData) {
        Room cq = gameData.getRoom("Captain's Quarters");
        for (GameItem it : cq.getItems()) {
			if (it instanceof NuclearDisc) {
				nukieDisk = (NuclearDisc)it;
				break;
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
					!(p.getCharacter() instanceof CaptainCharacter) &&
                    !(p.getCharacter() instanceof AICharacter)) {
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
			if (!opPlayers.contains(p) &&
					!(p.getCharacter() instanceof CaptainCharacter)) {
				opPlayers.add(p);
			}
		}
		

		for (int i = 0; i < getNoOfOperatives(gameData); ++i) {
			Player p = opPlayers.get(i);
			
			// Turn Character into decoy-npc
			//p.getCharacter().setClient(null);
			NPC npc = new HumanNPC(p.getCharacter(), p.getCharacter().getStartingRoom(gameData));
			p.getCharacter().setActor(npc);
			gameData.addNPC(npc);
			decoys.put(p, npc);
			GameCharacter opChar = new OperativeCharacter(num++, nukieShip.getID());
			
			p.setCharacter(opChar);
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
            p.addTolastTurnInfo("Infiltrate the station and find the nuclear disk. " +
                                "Then leave the station through an airlock. " +
                                "Your decoy is " + decoys.get(p).getBaseName() +
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
		
		c.addTolastTurnInfo(HTMLFont.makeText("red", "You are a nuclear operative!") +
							" Infiltrate the station and find the nuclear disk. " +
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
		return (new OperativesModeStats(gameData, this)).toString();
	}

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (getGameResult(gameData) == GameOver.SHIP_NUKED) {
            if (isAntagonist(value)) {
                if (!value.isDead()) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        } else {
            if (!value.isDead()) {
                return 1;
            } else {
                return 0;
            }
        }

    }


    public void setNuked(boolean b) {
		this.nuked  = b;
	}
	
	public boolean isNuked() {
		return this.nuked;
	}


	public boolean isOperative(Actor value) {
		if (value instanceof Player) {
			if (operatives.contains(value)) {
				return true;
			}
		}
		return false;
	}



	public NPC getDecoy(Actor value) {
		if (value instanceof Player) {
			return decoys.get(value);
		}
		return null;
	}



	
	

}
