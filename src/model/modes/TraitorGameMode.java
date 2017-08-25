package model.modes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.characters.general.AICharacter;
import model.characters.special.SpectatorCharacter;
import model.items.CosmicArtifact;
import model.items.NoSuchThingException;
import model.modes.objectives.*;
import model.npcs.*;
import model.npcs.animals.CatNPC;
import model.npcs.animals.ChimpNPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.robots.TARSNPC;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.general.MailBox;
import util.HTMLText;
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
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import model.objects.general.ElectricalMachinery;
import model.objects.consoles.GeneratorConsole;
import util.Pair;

public class TraitorGameMode extends GameMode {

	private static final double TRAITOR_FACTOR = 1.0/3.0;
	private static final int POINTS_FOR_CREW_PER_TRATIOR = 600;
	private static final int EVENT_FIX_POINTS = 20;
	private static final int POINTS_FOR_BROKEN_OBJECTS = 25;
    private static final int POINTS_FROM_DEAD_PIRATES = 50;
    private static final int POINTS_FROM_BOMBS_DEFUSED = 50;
    private static final int BAD_SECURITY = 10;
    private final List<GameItem> stealableItems;
    private List<Player> traitors = new ArrayList<>();
	private HashMap<Player, TraitorObjective> objectives = new HashMap<>();
	private String TRAITOR_START_STRING = HTMLText.makeText("orange", "You are a " + HTMLText.makeWikiLink("modes/traitor", "traitor") + "!");
	private String CREW_START_STRING = "There are traitors on the station. Find them and stop them before they ruin everything!";

    public  TraitorGameMode() {
        GameItem[] stealables = new GameItem[]{new ChefsHat(), new Bible(), new SunGlasses(), new GeigerMeter(), new KeyCard()};
        //GameItem[] stealables = new GameItem[]{ new KeyCard()};
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
	}

	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
		

	}

	private void assignTraitors(GameData gameData) {
		traitors = new ArrayList<>();
		for (Player p : gameData.getPlayersAsList()) {
			if (p.checkedJob("Traitor") &&
                    !(p.getCharacter() instanceof AICharacter) &&
                    !(p.getCharacter() instanceof SpectatorCharacter)) {
				traitors.add(p);
			}
		}



		// TO Few checked traitor, add som more randomly until we have
		// enough
		while (traitors.size() < getNoOfTraitors(gameData)) {
			throw new GameCouldNotBeStartedException("Can not play traitor mode. To few traitors.");
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
		if (val < 0.45 ) {
			List<Player> targets = new ArrayList<>();
			targets.addAll(gameData.getTargetablePlayers());
			targets.remove(traitor);
			if (targets.size() > 0) { 
				return new AssassinateObjective(traitor, MyRandom.sample(targets));
			} else {
				// KILL YOURSELF!
				return new AssassinateObjective(traitor, traitor);
			}
		} else if (val < 0.9)  {
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
		} else {
            return new MoneyObjective(gameData, traitor);
        }

			
		
		//return null;
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
		}

        //else if (allGoodGuysDead(gameData)) {
		//	return GameOver.PROTAGONISTS_DEAD;
		//}
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
		return HTMLText.makeWikiLink("modes/traitor", "Objective") + "; \"" + objectives.get(traitor).getText() + "\"";
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
		
	};

	@Override
	public String getSummary(GameData gameData) {
		return (new TraitorModeStats(gameData, this)).toString();
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
                Logger.log(value.getBaseName() + " is losing traitor and gets no points.");
                return 0;
            }
        }
        if (score  < 0) {
            if (!isAntagonist(value)) {
                Logger.log(value.getBaseName() + " is losing crew and gets no points.");
                return 0;
            }
        }
        Logger.log(value.getBaseName() + " is winner and gets a point!");
        return 1;
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
        result += pointsFromPirates(gameData);
        result += pointsFromBombsDefused(gameData);
        result += pointsFromSecurity(gameData);
        result += cosmicArtifactFound(gameData);
		return result;
	}

    public int pointsFromSecurity(GameData gameData) {
        try {
            int sum = 0;
            CrimeRecordsConsole crc = gameData.findObjectOfType(CrimeRecordsConsole.class);
            for (Map.Entry<Actor, List<Pair<String, Actor>>> criminalCrimeReporterEntry : crc.getReportsHistory().entrySet()) {
                Actor criminal = criminalCrimeReporterEntry.getKey();
                for (Pair<String, Actor> crimeReporterPair : criminalCrimeReporterEntry.getValue()) {
                    Actor reporter = crimeReporterPair.second;
                    if (reporter instanceof Player && !isAntagonist((Player) reporter)) {
                        if ((criminal instanceof Player && !isAntagonist((Player) criminal)) || isABadPersonNPC(criminal)) {
                            sum -= BAD_SECURITY * crc.getTimeForCrime(crimeReporterPair.first);
                        }
                    }
                }

            }
            return sum;

        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "Could not find Crime Records Console");
            return 0;
        }


    }

    private boolean isABadPersonNPC(Actor criminal) {
        if (criminal instanceof NPC) {
            NPC crimNPC = (NPC)criminal;
            if (crimNPC.isInfected()) {
                return true;
            }

           if (crimNPC.getActionBehavior() instanceof CrazyBehavior) {
               return true;
           }
        }
        return false;
    }

    public int pointsFromBombsDefused(GameData gameData) {
        return POINTS_FROM_BOMBS_DEFUSED * gameData.getGameMode().getBombsDefused();
    }

    public int pointsFromPirates(GameData gameData) {
        int res = 0;
        for (Actor a : gameData.getActors()) {
            if (a instanceof PirateNPC && a.isDead()) {
                res += POINTS_FROM_DEAD_PIRATES;
            }
        }
        return res;
    }


    public int pointsFromGod(GameData gameData) {
		Bible b = getBible(gameData);
        int biblePoints = 0;
		if (b == null) {
			Logger.log("Bible not found");
		} else {
            biblePoints = b.getGodPoints();
        }

        int mailboxpoints = 0;
        try {
            MailBox box = gameData.findObjectOfType(MailBox.class);
            mailboxpoints = box.getPoints();
        } catch (NoSuchThingException e) {
            Logger.log("Mailbox not found");
        }

        return biblePoints + mailboxpoints;
	}
	
	private Bible getBible(GameData gameData) {
		for (Actor a : gameData.getActors()) {
			try {
				Bible b = (Bible)GameItem.getItem(a, new Bible());
				return b;
			} catch (NoSuchThingException nse) {
//				Logger.log(Logger.CRITICAL, "Bible not found!");
			}
			
		}
		
		for (Room r : gameData.getRooms()) {
			for (GameItem it : r.getItems()) {
				if (it instanceof Bible) {
					//Logger.log("Found bible in room " + r.getName());
					return (Bible)it;
				}
			}
		}
		return null;
	}

	public int pointsFromPower(GameData gameData) {
        try {
            if (gameData.findObjectOfType(GeneratorConsole.class).getSource().getPowerOutput() > 0.99) {
                return 200;
            }
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "Generator console not found!");
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
    public boolean isAntagonist(Actor c) {
		return traitors.contains(c);
	}


    public int cosmicArtifactFound(GameData gameData) {
        for (Player pl : gameData.getPlayersAsList()) {
            for (GameItem it : pl.getItems()) {
                if (it instanceof CosmicArtifact) {
                    if (!isAntagonist(pl)) {
                        return 500;
                    } else {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "Current score: " + getScore(gameData) + ", Taitors: ";
    }

    @Override
    public List<Pair<Sprite, String>> getSpectatorContent(Actor whosAsking) {
        List<Pair<Sprite, String>> cont = new ArrayList<>();

        for (Actor a : traitors) {
            cont.add(new Pair<>(a.getCharacter().getSprite(whosAsking),
                                objectives.get(a).getText()));
        }

        return cont;
    }

    @Override
    public String getAntagonistName(Player p) {
        return "Traitor";
    }
}
