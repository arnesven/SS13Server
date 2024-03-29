package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.*;
import model.characters.special.SpectatorCharacter;
import model.events.Event;
import model.fancyframe.SinglePageFancyFrame;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.DecorativeRoom;
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
	private String PROT_MESSAGE = "There is a shape-shifting alien loose on the station. Kill it before it kills you!";

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
					!(p.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter)) &&
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
		Logger.log("Making an NPC out of " + ling.getCharacter().getFullName());
		Logger.log("Starting room was " + ling.getCharacter().getStartingRoom(gameData).getName());
		decoy = makeDecoy(ling, gameData);

		Room startRoom = super.getRandomStartRoom(gameData);

		lingChar = new ChangelingCharacter(startRoom);
		ling.setCharacter(lingChar);
        lingChar.getForm().setActor(ling);
		lingChar.getEquipment().removeEverything();
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
                    && gameData.getMap().getLevelForRoom(a.getPosition()).getName().equals(GameMap.STATION_LEVEL_NAME) &&
						!a.isInSpace()) {
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
		ling.addTolastTurnInfo("You are a " + HTMLText.makeWikiLink("modes#changeling", "changeling!") + " " +
				LING_START_STRING + decoyString());
	}

	private String decoyString() {
		return " Your decoy is " + decoy.getBaseName() + " (in " + decoy.getPosition().getName() + ").";
	}

	@Override
	protected void addAntagonistStartingMessage(Player c) {
		//c.addTolastTurnInfo(HTMLText.makeText("purple", "verdana", 3,
		c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!",  HTMLText.makeColoredBackground("purple",
				HTMLText.makeCentered(HTMLText.makeText("Aqua",
				"<br/><b>You are a " + HTMLText.makeWikiLink("modes#changeling", "changeling!</b><br/><br/>") +
						HTMLText.makeImage(ling.getSprite(null)) + "<br/>" +
				"Use your suction attack to absorb the essence of other creatures. When you feel strong enough, transform into your Ultimate Form. You must kill all the crew to win!" + decoyString())))));
    }

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo(PROT_MESSAGE);
	}

	@Override
	public String getModeDescription() {
		return PROT_MESSAGE;
	}

	@Override
	protected String getImageURL() {
		return "https://www.ida.liu.se/~erini02/ss13/thething.jpg";
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
    public List<Pair<Sprite, String>> getSpectatorContent(GameData gameData, Actor whosAsking) {
        List<Pair<Sprite, String>> list = new ArrayList<>();
        for (GameCharacter c : lingChar.getForms()) {
            if (!(c instanceof ParasiteCharacter || c instanceof ShamblingAbomination)) {
                list.add(new Pair(c.getSprite(whosAsking), c.getBaseName()));
            }
        }

        return list;
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

	@Override
	public Map<Player, NPC> getDecoys() {
		Map<Player, NPC> map = new HashMap<>();
		map.put(ling, decoy);
		return map;
	}
}
