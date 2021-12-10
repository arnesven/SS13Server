package model.modes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.characters.decorators.NoSuchInstanceException;
import model.characters.general.AICharacter;
import model.characters.special.SpectatorCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.map.GameMap;
import model.map.rooms.DecorativeRoom;
import model.map.rooms.HallwayRoom;
import model.modes.objectives.*;
import model.npcs.*;
import model.objects.general.*;
import util.HTMLText;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.TraitorCharacter;
import model.items.general.Bible;
import model.items.general.GameItem;
import model.items.general.Multimeter;
import model.items.general.UniversalKeyCard;
import model.items.general.PDA;
import model.items.suits.ChefsHat;
import model.items.suits.SunGlasses;
import model.map.rooms.Room;
import util.Pair;

public class TraitorGameMode extends ScoredGameMode {

	private static final double TRAITOR_FACTOR = 1.0/3.0;
	private static final int POINTS_FOR_CREW_PER_TRATIOR = 600;
	private final List<GameItem> stealableItems;
    private List<Player> traitors = new ArrayList<>();
	private HashMap<Player, TraitorObjective> objectives = new HashMap<>();
	private String TRAITOR_START_STRING = "You are a " + HTMLText.makeWikiLinkNew("modes#traitor", "traitor") + "!";
	private String CREW_START_STRING = "There are traitors on the station. Find them and stop them before they ruin everything!";

    public  TraitorGameMode() {
        GameItem[] stealables = new GameItem[]{new ChefsHat(), new Bible(), new SunGlasses(), new Multimeter(), new UniversalKeyCard()};
        //GameItem[] stealables = new GameItem[]{ new UniversalKeyCard()};
        ArrayList<GameItem> arr = new ArrayList<>();
        arr.addAll(Arrays.asList(stealables));
        stealableItems = arr;
    }

    @Override
    public String getName() {
        return "Traitor";
    }

    @Override
	protected void setUpOtherStuff(GameData gameData) { 
		assignTraitors(gameData);
		assignTraitorObjectivesAndGivePDAs(gameData);
	}


	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
		

	}

	protected void assignTraitors(GameData gameData) {
		traitors = new ArrayList<>();
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob(getName()) &&
                    !(p.getCharacter() instanceof AICharacter) &&
                    !(p.getCharacter() instanceof SpectatorCharacter)) {
				traitors.add(p);
			}
		}



		// TO Few checked traitor, add som more randomly until we have
		// enough
		while (traitors.size() < getNoOfTraitors(gameData)) {
			throw new GameCouldNotBeStartedException("Can not play " + getName() + " mode. To few traitors.");
		}
		
		// To Many checked traitor, removing some randomly until we have
		// just the right amount
		while (traitors.size() > getNoOfTraitors(gameData)) {
			Logger.log("Still to many traitors, removing one");
			Player notATraitor = MyRandom.sample(traitors);
			traitors.remove(notATraitor);
		}
	}


	protected void assignTraitorObjectivesAndGivePDAs(GameData gameData) {
		for (Player traitor : traitors) {
			TraitorObjective obj = createRandomObjective(traitor, gameData);
			traitor.setCharacter(new TraitorCharacter(traitor.getCharacter()));
			objectives.put(traitor, obj);
			traitor.addItem(new PDA(this), null);
		}
	}

    @Override
    protected void gameModeSpecificSetupForLateJoiner(Player newPlayer, GameData gameData) {
        if (MyRandom.nextDouble() < 0.34) {
            TraitorObjective obj = createRandomObjective(newPlayer, gameData);
            newPlayer.setCharacter(new TraitorCharacter(newPlayer.getCharacter()));
            objectives.put(newPlayer, obj);
            newPlayer.addItem(new PDA(this), null);
            traitors.add(newPlayer);
        }
    }

    private TraitorObjective createRandomObjective(Player traitor, GameData gameData) {

        if (stealableItems.size() > 0 && MyRandom.nextDouble() < 0.5) {
            GameItem it = MyRandom.sample(stealableItems);
            Logger.log("Steal-item is " + it.getBaseName());
            for (Player p : gameData.getPlayersAsList()) {
                if (GameItem.containsItem(p.getCharacter().getStartingItems(), it)) {
                    Logger.log(" " + p.getBaseName() + " has a " + it.getBaseName());
                    if (p != traitor) {
                        Logger.log(" match!.");
                        stealableItems.remove(it);
                        return new LarcenyObjective(gameData, traitor, p, it);
                    } else {
                        Logger.log(" but he was the traitor...");
                    }
                }
            }
            Logger.log("Did not find player for steal-item");
        }
		
		double val = MyRandom.nextDouble();
		if (val < 0.4 ) {
			List<Player> targets = new ArrayList<>();
			targets.addAll(gameData.getTargetablePlayers());
			targets.remove(traitor);
			if (targets.size() > 0) {
				return new AssassinateObjective(traitor, MyRandom.sample(targets));
			} else {
				// KILL YOURSELF!
				return new AssassinateObjective(traitor, traitor);
			}
		} else if (val < 0.8) {
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
        } else if (val < 0.9) {
            List<Room> funRoomsToDestroy = new ArrayList<>();
            funRoomsToDestroy.addAll(gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME));
            funRoomsToDestroy.removeIf((Room r) -> r instanceof HallwayRoom || r instanceof DecorativeRoom || r.isHidden());

            return new DestroyRoomObjective(gameData, MyRandom.sample(funRoomsToDestroy));
		} else {
            return new MoneyObjective(gameData, traitor);
        }

			
		
		//return null;
	}


	protected int getNoOfTraitors(GameData gameData) {
		double d = gameData.getPlayersAsList().size() * TRAITOR_FACTOR;
		int traitors = Math.max(1, (int)Math.round(d));
		Logger.log("No of traitors " + traitors);
		return traitors;
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
		//for (Player traitor : traitors) {
		//	traitor.addTolastTurnInfo(getObjectiveText(traitor));
		//}
	}

	protected String getObjectiveText(Player traitor) {
		return HTMLText.makeWikiLinkNew("modes#" + getName().toLowerCase(), "Objective") + "; \"" +
                objectives.get(traitor).getText() + "\"";
	}


	
	@Override
	protected void triggerModeSpecificEvents(GameData gameData) {
		for (TraitorObjective obj : objectives.values()) {
			obj.isCompleted(gameData);
		}
	}
	
	@Override
	public void doWhenGameOver(GameData gameData) {
		triggerModeSpecificEvents(gameData);
	}

	@Override
	public String getSummary(GameData gameData) {
		return (new TraitorModeStats(gameData, this)).toString();
	}

	@Override
	protected int getModeSpecificPoints(GameData gameData) {
		int result = 0;
		result += pointsFromObjectives(gameData);
		result += pointsFromSavedCrew(gameData);
		result += extraExtendedPoints(gameData);
		return result;

	}

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (value.isDead()) {
            Logger.log(value.getBaseName() + " is dead and gets no points.");
            return 0;
        }
        int score = getScore(gameData);
        if ( score > 0) {
            if (isAntagonist(value)) {
                 if (objectives.get(value).isCompleted(gameData)) {
                     Logger.log(value.getBaseName() + " is losing traitor but gets 1 from completed objective.");
                     return 1;
                 }
                Logger.log(value.getBaseName() + " is losing traitor and gets no points.");
                return 0;
            }
        } else if (score  < 0) {
            if (!isAntagonist(value)) {
                Logger.log(value.getBaseName() + " is losing crew and gets no points.");
                return 0;
            } else {
                if (objectives.get(value).isCompleted(gameData)) {
                    Logger.log(value.getBaseName() + " is winning traitor with completed objective.");
                    return 2;
                } else {
                    Logger.log(value.getBaseName() + " is winning traitor with failed objective.");
                    return 1;
                }
            }
        }
        Logger.log(value.getBaseName() + " is a surviving player in a draw game => 1 point");
        return 1;
    }

    public List<Player> getTraitors() {
		return traitors;
	}
	
	public Map<Player, TraitorObjective> getObjectives() {
		return objectives;
	}

	protected int extraExtendedPoints(GameData gameData) {
		return 0;
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
            a.getCharacter().isCrew();
			if (!(a instanceof Player || a instanceof HumanNPC) || !a.getCharacter().isCrew()) {
				it.remove();
			}
		}

		int stillAlive = 0;
		for (Actor act : shouldBeSaved) {
			if (!act.isDead()) {
				stillAlive++;
			}
		}
		
		return (int)( (stillAlive * getTotalPointsForCrew(gameData))
					 / shouldBeSaved.size()   );
	}

	private double getTotalPointsForCrew(GameData gameData) {
		return traitors.size()*POINTS_FOR_CREW_PER_TRATIOR;
	}


	@Override
	protected void addAntagonistStartingMessage(Player c) {
		//c.addTolastTurnInfo(TRAITOR_START_STRING);
		//c.addTolastTurnInfo(getObjectiveText(c));
		setAntagonistFancyFrame(c);
	}

	public void setAntagonistFancyFrame(Player c) {
		c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!",
                HTMLText.makeColoredBackground("Yellow", HTMLText.makeCentered("<br/><br/><b>" +
				HTMLText.makeText("Red", TRAITOR_START_STRING) + "</b><br/>" +
                        HTMLText.makeImage(new PDA(this).getSprite(null)) + "<br/>" +
                getObjectiveText(c) + "<br/>" + "<i>You can access this dialog again by using your PDA.</i>"))));
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo(CREW_START_STRING);
		
	}

	@Override
	public String getModeDescription() {
		return CREW_START_STRING;
	}

	@Override
    public boolean isAntagonist(Actor c) {
		return traitors.contains(c);
	}


    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "Current score: " + getScore(gameData) + ", Taitors: ";
    }

    @Override
    public List<Pair<Sprite, String>> getSpectatorContent(GameData gameData, Actor whosAsking) {
        List<Pair<Sprite, String>> cont = new ArrayList<>();

        for (Actor a : traitors) {
            cont.add(new Pair<>(a.getCharacter().getSprite(whosAsking),
                                objectives.get(a).getText() + " [" + (objectives.get(a).isCompleted(gameData)?"OK":"NO") + "]"));
        }

        return cont;
    }

    @Override
    public String getAntagonistName(Player p) {
        return "Traitor";
    }

	@Override
	public Map<Player, NPC> getDecoys() {
		return new HashMap<>();
	}

	public void removeTraitor(Player p) {
	    try {
            p.removeInstance((GameCharacter gc) -> gc instanceof TraitorCharacter);
        } catch (NoSuchInstanceException nsie) {
	        Logger.log("Could not remove traitor decorator from player!");
        }
        traitors.remove(p);
        objectives.remove(p);
        p.getCharacter().getItems().removeIf((GameItem it ) -> it instanceof PDA);
    }

    @Override
	public String getImageURL() {
		return "https://www.ida.liu.se/~erini02/ss13/traitors.jpg";
	}


}
