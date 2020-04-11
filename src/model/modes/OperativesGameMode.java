package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.characters.general.AICharacter;
import model.characters.special.SpectatorCharacter;
import model.events.NoPressureEverEvent;
import model.items.NoSuchThingException;
import model.objects.general.NuclearBomb;
import util.HTMLText;
import util.Logger;
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
import model.map.rooms.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import util.Pair;

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
        Room cq = null;
        try {
            cq = gameData.getRoom("Captain's Quarters");
        } catch (NoSuchThingException e) {
            cq = MyRandom.sample(gameData.getRooms());
        }
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
        try {
            nukieShip = gameData.getRoom("Nuclear Ship");
            int num = 1;

            List<Player> opPlayers = new ArrayList<>();

            for (Player p : gameData.getPlayersAsList()) {
                if (p.checkedJob("Operative") &&
                        !(p.getCharacter() instanceof CaptainCharacter) &&
                        !(p.getCharacter() instanceof AICharacter) &&
                        !(p.getCharacter() instanceof SpectatorCharacter)) {
                    opPlayers.add(p);
                }
            }

            if (opPlayers.size() < getNoOfOperatives(gameData)) {
                throw new GameCouldNotBeStartedException("Could not play operatives mode. Too few operatives.");
            }

            while (opPlayers.size() > getNoOfOperatives(gameData)) {
                // Too many operatives, remove some.
                opPlayers.remove(MyRandom.sample(opPlayers));
            }


            for (int i = 0; i < getNoOfOperatives(gameData) && i < opPlayers.size(); ++i) {
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
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
	}

	protected int getNoOfOperatives(GameData gameData) {
		return (int)Math.floor(gameData.getTargetablePlayers().size() * OPERATIVE_FACTOR);
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
            p.addTolastTurnInfo("You are an " + HTMLText.makeLink(HTMLText.wikiURL + "/modes/operatives", "operative") +
                                ". Infiltrate the station and find the nuclear disk. " +
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

		StringBuilder data = new StringBuilder( HTMLText.makeCentered(HTMLText.makeText("White", "<br/><b>You are a nuclear " +
                            HTMLText.makeLink(HTMLText.wikiURL + "/modes/operatives", "operative") + "!</b><br/>") +
							HTMLText.makeText("Black", "<br/>Infiltrate the station and find the nuclear disk.<br/>" +
							"Then leave the station through an airlock.<br/>" +
							"You can pretend to be the " + decoys.get(c).getBaseName() + 
							" (in " + decoys.get(c).getPosition().getName() + ")<br/><br/>")));
		if (!decoyStr.toString().equals("")) {
			data.append("Other decoys are " + decoyStr.toString());
		}

		c.getFancyFrame().setData("Important!", false, HTMLText.makeColoredBackground("Red", data.toString()));
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo("Nuclear operatives are infiltrating the station. Prevent them from getting the nuclear disk. If they leave the station with it, they will nuke the station!");
	}

	@Override
    public boolean isAntagonist(Actor c) {
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
                Logger.log(value.getBaseName() + " was operative and ");
                if (!value.isDead()) {
                    Logger.log("     isn't dead => 2 points! ");
                    return 2;
                } else {
                    Logger.log("     is dead as a dodo => 1 points ");
                    return 1;
                }
            } else {
                return 0;
            }
        } else {

            if (!value.isDead()) {
                Logger.log(value.getBaseName() + " ISNT DEAD => 1 point!");
                return 1;
            } else {
                Logger.log(value.getBaseName() + " is pushing up the daisies. 0 pts");
                return 0;
            }
        }

    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "Disk in: " + nukieDisk.getPosition().getName();
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


    @Override
    public String getAntagonistName(Player p) {
        return "Operative";
    }
}
