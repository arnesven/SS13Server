package model.modes;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.*;
import model.characters.special.SpectatorCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.GameMap;
import util.HTMLText;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.CaptainCharacter;
import model.events.ChangelingSensesEvent;
import model.map.rooms.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import util.Pair;

public class ChangelingGameMode extends GameMode {

	private static final String LING_START_STRING = "Kill everyone!";
	private Player ling;
	private ChangelingCharacter lingChar;
	private NPC decoy;
    private boolean changelingExposed = false;
    private List<Actor> actorsWhoStartedTheGame;

    @Override
    public String getName() {
        return "Changeling";
    }

    @Override
	protected void setUpOtherStuff(GameData gameData) {
        Event e = new ChangelingSensesEvent(ling);
		//gameData.addMovementEvent(e);
        gameData.addEvent(e);
		getEvents().remove("random husks");
        actorsWhoStartedTheGame = new ArrayList<>();
        actorsWhoStartedTheGame.addAll(gameData.getActors());
	}

	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
			GameData gameData) {


		List<Player> lingPlayers = new ArrayList<>();
		
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Changeling") && 
					!(p.getCharacter() instanceof CaptainCharacter) &&
                    !(p.getCharacter() instanceof AICharacter) &&
                    !(p.getCharacter() instanceof SpectatorCharacter)) {
				lingPlayers.add(p);
			}
		}

        if (lingPlayers.isEmpty()) {
            throw new GameCouldNotBeStartedException("Can't play changeling mode!");
        }
		
		while (lingPlayers.size() > 1) { // Too many lings, remove some.
			lingPlayers.remove(MyRandom.sample(lingPlayers));
		}
	
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.addAll(gameData.getPlayersAsList());
		while (lingPlayers.size() < 1) { // Too few checked ling add some.
			Player p = MyRandom.sample(allPlayers);
			if (!lingPlayers.contains(p) && 
					!(p.getCharacter() instanceof CaptainCharacter)) {
				lingPlayers.add(p);
			}
		}
		
		ling = MyRandom.sample(lingPlayers);
		
		NPC npc = new HumanNPC(ling.getCharacter(), 
							   ling.getCharacter().getStartingRoom(gameData));
		ling.getCharacter().setActor(npc);

		gameData.addNPC(npc);
		decoy = npc;
		
		Room startRoom;
		do {
			startRoom = MyRandom.sample(gameData.getRooms());
		} while (isAStartingRoom(startRoom, gameData) || lockedRoom(startRoom, gameData));
		
		lingChar = new ChangelingCharacter(startRoom);
		ling.setCharacter(lingChar);
        lingChar.getForm().setActor(ling);
		lingChar.removeSuit();
		
	}

	private boolean lockedRoom(Room startRoom, GameData gameData) {
        try {
            return startRoom == gameData.getRoom("Brig") || startRoom == gameData.getRoom("Armory");
        } catch (NoSuchThingException e) {
            return true;
        }
    }

	private boolean isAStartingRoom(Room startRoom, GameData gameData) {
		for (Player p : gameData.getPlayersAsList()) {
			if (p.getCharacter().getStartingRoom() == startRoom.getID()) {
				return true;
			}
		}
		return false;
	}

	protected GameOver getGameResult(GameData gameData) {
		if (gameData.isAllDead()) {
			return GameOver.ALL_DEAD;
		} else if (allCrewDead(gameData)) {
			return GameOver.PROTAGONISTS_DEAD;
		} else if (ling.isDead()) {
			return GameOver.ANTAGONISTS_DEAD;
		} else if (gameData.getRound() == lengthDependingOnTargets(gameData)) {
            return GameOver.TIME_IS_UP;
        }
		return null;
	}

    private int lengthDependingOnTargets(GameData gameData) {
        return (int)(Math.min(gameData.getNoOfRounds(),
                Math.ceil(actorsWhoStartedTheGame.size()*2.5)));

    }

    private boolean allCrewDead(GameData gameData) {
		for (Actor a : actorsWhoStartedTheGame) {
            try {
                if (a.getCharacter().isCrew() && !a.isDead()
                    && gameData.getMap().getLevelForRoom(a.getPosition()).equals(GameMap.STATION_LEVEL_NAME)) {
                    return false;
                }
            } catch (NoSuchThingException e) {

            }
        }
		return true;
	}

	@Override
	public boolean gameOver(GameData gameData) {
        GameOver over = getGameResult(gameData);
        if (over != null) {
            exposeChangeling();
        }

		return over != null;
	}

    private void exposeChangeling() {
        if (!changelingExposed) {
            ling.setCharacter(new CharacterDecorator(ling.getCharacter(), "Exposed Changeling") {
                                  @Override
                                  public String getBaseName() {
                                      return "Changeling";
                                  }
                              });
            changelingExposed = true;
        }
    }

    @Override
	public void setStartingLastTurnInfo() {
		ling.addTolastTurnInfo("You are a " + HTMLText.makeLink(HTMLText.wikiURL + "/modes/changeling", "changeling!") + " " + LING_START_STRING + decoyString());
	}

	private String decoyString() {
		return " Your decoy is " + decoy.getBaseName() + " (in " + decoy.getPosition().getName() + ").";
	}

	@Override
	protected void addAntagonistStartingMessage(Player c) {
		c.addTolastTurnInfo(HTMLText.makeText("purple", "verdana", 3, "You are a " + HTMLText.makeLink(HTMLText.wikiURL + "/changelingmode", "changeling!")) + "Use your suction attack to absorb the essence of hidden creatures. You must be the sole survivor to win!" + decoyString());
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo("There is a shape-shifting alien loose on the station. Kill it before it kills you!");
	}

	@Override
    public boolean isAntagonist(Actor c) {
		return c == ling;
	}

	@Override
	public String getSummary(GameData gameData) {
		return (new ChangelingModeStats(gameData, this)).toString();
	}

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (value.isDead()) {
            Logger.log(value.getBaseName() + " is dead, no points.");
            return 0;
        }
        if (getGameResult(gameData) == GameOver.PROTAGONISTS_DEAD) {
            if (isAntagonist(value)) {
                Logger.log(value.getBaseName() + " is winning changeling! 3 POINTS!");
                return 3;
            }
        }
        if (isAntagonist(value)) {
            Logger.log(value.getBaseName() + " is losing changeling. 0 points.");
            return 0;
        }
        Logger.log(value.getBaseName() + " gets a point.");
        return 1;
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "Current power: " + String.format("%1$.1f", lingChar.getPower()) + ",  sucked from:";
    }

    @Override
    public List<Pair<Sprite, String>> getSpectatorContent(Actor whosAsking) {
        List<Pair<Sprite, String>> list = new ArrayList<>();
        for (GameCharacter c : lingChar.getForms()) {
            if (!(c instanceof ParasiteCharacter || c instanceof HorrorCharacter)) {
                list.add(new Pair(c.getSprite(whosAsking), c.getBaseName()));
            }
        }

        return super.getSpectatorContent(whosAsking);
    }

    public Actor getChangeling() {
		return ling;
	}

	public ChangelingCharacter getChangelingChar() {
		return lingChar;
	}

    @Override
    public String getAntagonistName(Player p) {
        return "Changeling";
    }
}
