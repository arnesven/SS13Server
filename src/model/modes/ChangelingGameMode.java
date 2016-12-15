package model.modes;

import java.util.ArrayList;
import java.util.List;

import model.characters.decorators.CharacterDecorator;
import model.characters.general.AICharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import util.HTMLText;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.crew.CaptainCharacter;
import model.events.ChangelingSensesEvent;
import model.map.rooms.Room;
import model.npcs.HumanNPC;
import model.npcs.NPC;

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

        if (gameData.getPlayersAsList().size() < 2) {
            throw new IllegalStateException("Cannot play changeling with 2 players!");
        }

		List<Player> lingPlayers = new ArrayList<>();
		
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Changeling") && 
					!(p.getCharacter() instanceof CaptainCharacter) &&
                    !(p.getCharacter() instanceof AICharacter) ) {
				lingPlayers.add(p);
			}
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
		} else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        }
		return null;
	}
	
	private boolean allCrewDead(GameData gameData) {
		for (Actor a : actorsWhoStartedTheGame) {
			if (a.getCharacter().isCrew() && !a.isDead()) {
				return false;
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
            return 0;
        }
        if (getGameResult(gameData) == GameOver.PROTAGONISTS_DEAD) {
            if (isAntagonist(value)) {
                return 3;
            }
        }
        if (isAntagonist(value)) {
            return 0;
        }
        return 1;
    }

    public Actor getChangeling() {
		return ling;
	}

	public ChangelingCharacter getChangelingChar() {
		return lingChar;
	}

}
