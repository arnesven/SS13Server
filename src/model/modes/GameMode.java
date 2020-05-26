package model.modes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import graphics.sprites.Sprite;
import model.*;
import model.characters.crew.*;
import model.characters.decorators.HostCharacter;
import model.characters.general.*;
import model.characters.special.SpectatorCharacter;
import model.characters.visitors.VisitorCharacter;
import model.events.*;
import model.events.ambient.*;
import model.items.EmergencyKit;
import model.items.NoSuchThingException;
import model.items.RandomItemManager;
import model.items.suits.Rapido;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.DecorativeRoom;
import model.map.rooms.LateJoiningShuttle;
import model.misc.ChristmasBooster;
import model.modes.goals.PersonalGoalAssigner;
import model.npcs.*;
import model.npcs.animals.SnakeNPC;
import model.npcs.robots.TARSNPC;
import model.objects.ai.AIMemory;
import model.objects.ai.AITurret;
import model.objects.general.ATM;
import model.objects.consoles.AIConsole;
import model.objects.general.JunkVendingMachine;
import util.HTMLText;
import util.Logger;
import util.MyRandom;
import model.items.general.GameItem;
import model.map.rooms.Room;
import util.Pair;

/**
 * @author erini02
 *
 */
public abstract class GameMode implements Serializable {


	private static String[] knownModes = { "Traitor", "Host", "Operatives", "Changeling", "Rogue AI", "Armageddon", "Mutiny", "Wizard", "Mixed", "Secret", "Creative"};
	private Bank bank;
	private Map<String,Event> events = new HashMap<>();
	protected ArrayList<NPC> allParasites = new ArrayList<NPC>();
    private int defusedBombs = 0;
    private int maxBombChain;
    private List<String> miscHappenings = new ArrayList<>();
    private boolean hallOfFameUpdated = false;
    private Player aIPlayer = null;
    private Player capCl = null;
    private List<GameCharacter> remainingChars;
    private PersonalGoalAssigner tasks;
    private LateJoiningShuttle lateJoiningShuttle;

    public GameMode() {
    	AmbientEvent.setUpAmbients(events);
	}

    public static String[] getAvailableModes() {
        return knownModes;
    }

    public abstract String getName();
    
	public void doWhenGameOver(GameData gameData) {
		
	}

	private static HashMap<String, GameCharacter> availableChars() {
		HashMap<String, GameCharacter> availableChars = new HashMap<>();
		availableChars.put("Captain",          new CaptainCharacter());
		availableChars.put("Head of Staff",    new HeadOfStaffCharacter());
		availableChars.put("Security Officer", new SecurityOfficerCharacter());
		availableChars.put("Detective",        new DetectiveCharacter());
		availableChars.put("Quartermaster",    new QuarterMasterCharacter());
		availableChars.put("Doctor",           new DoctorCharacter());
		availableChars.put("Biologist",        new BiologistCharacter());
		availableChars.put("Engineer",         new EngineerCharacter());
		availableChars.put("Science Officer",  new ScienceOfficerCharacter());
		availableChars.put("Geneticist",       new GeneticistCharacter());
        availableChars.put("Chemist",          new ChemistCharacter());
        availableChars.put("Roboticist",       new RoboticistCharacter());
        availableChars.put("Security Guard",   new SecurityGuardCharacter());
		availableChars.put("Chef",             new ChefCharacter());
		availableChars.put("Bartender",        new BartenderCharacter());
		availableChars.put("Architect",        new ArchitectCharacter());
		availableChars.put("Chaplain",         new ChaplainCharacter());
        availableChars.put("Miner",            new MinerCharacter());
        availableChars.put("Janitor",          new JanitorCharacter());
        availableChars.put("Staff Assistant",  new StaffAssistantCharacter());
		availableChars.put("Visitor",          new VisitorCharacter("Visitor", 0, 0.0){
            @Override
            public String getPublicName(Actor whosAsking) {
                return getPublicName();
            }

            public VisitorCharacter clone() {
                throw new IllegalStateException("Should not have been called!");
            }

        });

		return availableChars;
	}

    public static List<GameCharacter> getAllCrew() {
        List<GameCharacter> gcs = new ArrayList<>();
        gcs.addAll(availableChars().values());
        return gcs;
    }

	public static List<String> getAllCrewAsStrings() {
		List<String> gcs = new ArrayList<>();
		gcs.addAll(availableChars().keySet());
		return gcs;
	}

	public static List<String> getAllCharsAsStrings() {
		List<String> gcs = new ArrayList<>();
		gcs.addAll(getAllCrewAsStrings());
		Collections.sort(gcs);
		gcs.add("TARS");
		gcs.add("Cat");
		gcs.add("Chimp");
		return gcs;
	}
    public boolean hasUpdatedHallOfFame() {
        return hallOfFameUpdated;
    }

    public void setUpdatedHallOfFame(boolean updatedHallOfFame) {
        this.hallOfFameUpdated = updatedHallOfFame;
    }


	/**
	 * This method is called as the last part of the setup.
	 * In this step, the game mode can set up hidden things
	 * which pertains to this game mode.
	 * @param gameData
	 */
	protected abstract void setUpOtherStuff(GameData gameData);

	public abstract String getSpectatorSubInfo(GameData gameData);
	public abstract String getAntagonistName(Player p);
	public abstract Map<Player, NPC> getDecoys();


	/**
	 * Overload this method to add roles "ontop" of existing ones.
	 * E.g. the host role is assigned in this method. 
	 * Or traitors in traitor-mode.
	 * @param listOfCharacters
	 * @param gameData
	 */
	protected abstract void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData);



	/**
	 * Checks wether or not the game is over or not.
	 * @param gameData
	 * @return true if the game is over, false otherwise.
	 */
	public abstract boolean gameOver(GameData gameData);



	/**
	 * This method will be called at the start of each turn,
	 * to set the info for players.
	 */
	public abstract void setStartingLastTurnInfo();

	/**
	 * This method should send a starting message to the antagonist c
	 * at the start of the game
	 * @param c
	 */
	protected abstract void addAntagonistStartingMessage(Player c);

	/**
	 * This method should send a starting message to the protagonist c
	 * at the start of the game.
	 * @param c
	 */
	protected abstract void addProtagonistStartingMessage(Player c);

	protected abstract String getModeDescription();
	protected abstract String getImageURL();


		/**
         * This method checks if this player is a antagonist or not.
         * @param c
         * @return
         */
	public abstract boolean isAntagonist(Actor c);


	/**
	 * This method gets the game summary which is displayed
	 * to the clients after the game is over. The summary
	 * can look very different depending on the game mode.
	 * @param gameData
	 * @return
	 */
	public abstract String getSummary(GameData gameData);

    /**
     * At the end of the game, players gain points for
     * survival/completed objectives. This is then summed
     * and displayed in the Hall of Fame.
     * @param value
     * @return
     */
    public abstract Integer getPointsForPlayer(GameData gameData, Player value);


    protected void gameModeSpecificSetupForLateJoiner(Player newPlayer, GameData gameData) {

    }


	protected List<GameCharacter> getAllCharacters() {
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		return list;
	}

	public void setup(GameData gameData) {
		Logger.log("Game Modes: Running power simulation once");
		events.get("simulate power").apply(gameData);
		Logger.log("Game Modes: Going to assign roles");
		remainingChars = assignCharactersToPlayers(gameData);
		Logger.log(" Game Mode: Setup: Characters assigned");
		for (Player p : gameData.getPlayersAsList()) {
			try {
				Logger.log("  " + gameData.getClidForPlayer(p) + " - " + p.getCharacter().getFullName());
			} catch (NoSuchThingException e) {
				e.printStackTrace();
			}
		}
		moveCharactersIntoStartingRooms(gameData);
		Logger.log(" Game Mode: Setup: Characters moved into starting rooms");
		addNPCs(gameData, remainingChars);
		Logger.log(" Game Mode: Setup: NPCs added");
		giveCharactersStartingItems(gameData);
		Logger.log(" Game Mode: Setup: Chars got starting items");
		setUpOtherStuff(gameData);
		Logger.log(" Game Mode: Other stuff setupped");
		addStuffToRooms(gameData);
		addRandomItemsToRooms(gameData);
		Logger.log(" Game Mode: Items added to rooms");
		addPersonalGoals(gameData);

		Logger.log("Game Mode: Creating Bank");
		this.bank = new Bank(gameData);

		addStartingMessages(gameData);
		if (LocalDateTime.now().getMonth().equals(Month.DECEMBER)) {
		    Logger.log("Santa clause is coming to town!");
            ChristmasBooster.addStuff(gameData);
        } else {
            Logger.log("Santa stays at the north pole.");
        }

	}

    protected void addPersonalGoals(GameData gameData) {
        tasks = new PersonalGoalAssigner(gameData);
        tasks.addTasks(gameData);
    }


    protected void addStartingMessages(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			addStartingMessage(gameData, c);
		}
	}

    private void addStartingMessage(GameData gameData, Player c) {
        if (isAntagonist(c)) {
            addAntagonistStartingMessage(c);
        } else {
            addProtagonistStartingMessage(c);
        }
        if (aIPlayer != null) {
            try {
                if (c == aIPlayer) {
                    c.setFancyFrame(((AICharacter)c.getInnermostCharacter()).getStartingFancyFrame());
                    c.addTolastTurnInfo(gameData.getClidForPlayer(capCl) + " is the Captain.");
                } else {
                    c.addTolastTurnInfo(HTMLText.makeText("blue", gameData.getClidForPlayer(aIPlayer) + " is the AI."));
                }
            } catch (NoSuchThingException nste) {
                nste.printStackTrace();
            }
        }
    }


    /**
	 * this method is called as the first step of the setup
	 * at the beginning of the game. Players should receive
	 * their characters in this step.
	 * @param gameData
	 */
	protected final List<GameCharacter> assignCharactersToPlayers(GameData gameData) {
		ArrayList<Player> listOfClients = new ArrayList<Player>();
		listOfClients.addAll(gameData.getPlayersAsList());

        removeSpectators(listOfClients, gameData);

        if (listOfClients.isEmpty()) {
            throw new GameCouldNotBeStartedException("No remaining non-spectator players");
        }

		Collections.shuffle(listOfClients);
		
		ArrayList<GameCharacter> listOfCharacters = new ArrayList<>();
		listOfCharacters.addAll(getAllCharacters());

		/// SELECT A CAPTAIN, SS13 MUST ALWAYS HAVE A CAPTAIN
		capCl = selectCaptain(listOfClients, listOfCharacters);
        Logger.log("Captain assigned");

        /// ASSIGN AN AI-PLAYER IF ABLE
        selectAIPlayer(listOfClients, gameData);

		/// ASSIGN ROLES RANDOMLY
		assignRestRoles(listOfClients, listOfCharacters, gameData);
        Logger.log("Other roles assigned");
		assignOtherRoles(listOfCharacters, gameData);
        Logger.log("Mode specific roles assigned");

		return listOfCharacters;
	}

    protected void selectAIPlayer(ArrayList<Player> listOfClients, GameData gameData) {
        List<Player> playersWhoWantToBeAI = new ArrayList<>();
        for (Player pl : listOfClients) {
            if (pl.checkedJob("Artificial Intelligence")) {
                playersWhoWantToBeAI.add(pl);
            }
        }

        try {
            AIConsole console = gameData.findObjectOfType(AIConsole.class);
            if (playersWhoWantToBeAI.size() == 0 || MyRandom.nextDouble() < 0.25) { // chance of no AI even though at least one person wanted to be one.
                gameData.findObjectOfType(AITurret.class).addPassiveTurretEvent(console, gameData);
                return;
            }

            console.setAIisPlayer(true);
            aIPlayer = MyRandom.sample(playersWhoWantToBeAI);
            console.setAIPlayer(aIPlayer);
            aIPlayer.setCharacter(new AICharacter(gameData.getRoom("AI Core").getID(), console, false));
            listOfClients.remove(aIPlayer);
            events.remove("corrupt ai");
            gameData.getRoom("AI Core").addObject(new AIMemory(aIPlayer, gameData.getRoom("AI Core")));

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


    }


    protected Player selectCaptain(ArrayList<Player> clientsRemaining,
                                   ArrayList<GameCharacter> listOfCharacters) {

		ArrayList<Player> playersWhoSelectedCaptain = new ArrayList<>();
		for (Player pl : clientsRemaining) {
			if (pl.checkedJob("Captain")) {
				playersWhoSelectedCaptain.add(pl);
			}
		}

		if (playersWhoSelectedCaptain.size() == 0) {
			playersWhoSelectedCaptain.addAll(clientsRemaining);
		}


		Player capCl = MyRandom.sample(playersWhoSelectedCaptain);
		
		clientsRemaining.remove(capCl);
		GameCharacter gc = null;
		for (GameCharacter ch : listOfCharacters) {
			if (ch.getBaseName().equals("Captain")) {
				capCl.setCharacter(ch);
				gc = ch;
				break;
			}
		}
		listOfCharacters.remove(gc);
        return capCl;
    }

	protected void assignRestRoles(ArrayList<Player> remainingPlayers,
			ArrayList<GameCharacter> remainingCharacters, GameData gameData) {
        Logger.log("Assigning hidden roles, players remaining: " + remainingPlayers.size());
		Collections.shuffle(remainingPlayers);

		while (remainingPlayers.size() > 0) {
			Player cl = remainingPlayers.remove(0);

			ArrayList<GameCharacter> candidates = new ArrayList<>();
			for (GameCharacter gc : remainingCharacters) {
				if (cl.checkedJob(gc.getBaseName())) {
					candidates.add(gc);
				}
			}
			if (candidates.size() == 0) {
				candidates.addAll(remainingCharacters);
			}

			GameCharacter selected = candidates.remove(MyRandom.nextInt(candidates.size()));
            setPlayersCharacter(cl, selected, remainingCharacters);

            if (remainingCharacters.size() == 0) {
            	Logger.log(Logger.CRITICAL, "Ran out of characters! Adding a...");
            	if (MyRandom.nextDouble() < 0.5) {
					remainingCharacters.add(new StaffAssistantCharacter());
					Logger.log(Logger.CRITICAL, "... Staff Assistant");
				} else {
					Logger.log(Logger.CRITICAL, "... Visitor");
            		remainingCharacters.add(new VisitorCharacter("Visitor", 0, 0.0) {
						@Override
						public GameCharacter clone() {
							throw new IllegalStateException("Should not have been called!");
						}
					});
				}
			}

		}
	}

    private void setPlayersCharacter(Player cl, GameCharacter selected,
                                     List<GameCharacter> remainingCharacters) {
        GameCharacter chAr = selected;
        if (selected instanceof VisitorCharacter) {
            chAr = MyRandom.sample(((VisitorCharacter)selected).getSubtypes());
        }
        cl.setCharacter(chAr);
        remainingCharacters.remove(selected);
    }

    private void removeSpectators(ArrayList<Player> listOfClients, GameData gameData) {
        List<Player> done = new ArrayList<>();
        for (Player p : listOfClients) {
            if (p.getSettings().get(PlayerSettings.MAKE_ME_A_SPECTATOR)) {
                p.setCharacter(new SpectatorCharacter(gameData));
                done.add(p);
                p.moveIntoRoom(p.getCharacter().getStartingRoom(gameData));
            }
        }

        listOfClients.removeAll(done);
    }



    private void moveCharactersIntoStartingRooms(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
            Room startRoom = null;
            startRoom = c.getCharacter().getStartingRoom(gameData);
            Logger.log("Starting room for " + c.getName() + " is " + startRoom.getName());
            c.moveIntoRoom(startRoom);
			c.setNextMove(c.getPosition().getID());
		}
	}

	protected void addNPCs(GameData gameData, List<GameCharacter> remainingChars) {

        try {
		while (MyRandom.nextDouble() < 0.5) {
                gameData.addNPC(new SnakeNPC(gameData.getRoom("Greenhouse")));
        }
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "No greenhouse to put snakes in!");
            e.printStackTrace();
        }

        TARSNPC.addATarsToRandomRoom(gameData);

        MouseNPC.addAMouseToRandomRoom(gameData);


	//	testShortestDistance(gameData);

		int noOfNPCs = noOfNPCs();
		for ( ; noOfNPCs > 0 ; noOfNPCs--) {
			GameCharacter gc;
			if (remainingChars.size() == 0) {
				gc = new VisitorCharacter("asdf") {
                    @Override
                    public String getPublicName(Actor whosAsking) {
                        return null;
                    }

                    @Override
                    public GameCharacter clone() {
                        return null;
                    }
                };
			} else {
				gc = remainingChars.remove(MyRandom.nextInt(remainingChars.size()));
			}
            if (gc instanceof VisitorCharacter) {
                gc = MyRandom.sample(((VisitorCharacter)gc).getSubtypes());
            }
			NPC human = new HumanNPC(gc, gc.getStartingRoom(gameData));
			gameData.addNPC(human);
			Logger.log("Adding npc " + gc.getBaseName());

		}
	}



    private int noOfNPCs() {
        return Math.min(MyRandom.nextInt(3) + 5, remainingChars.size());
        //return Math.min(16, remainingChars.size());
    }


    private void giveCharactersStartingItems(GameData gameData) {
		List<Actor> actors = new ArrayList<Actor>();
		actors.addAll(gameData.getPlayersAsList());
		actors.addAll(gameData.getNPCs());



		for (Actor c : actors) {
            c.giveStartingItemsToSelf();

		}
	}

	protected void addStuffToRooms(GameData gameData) {
        Room r = MyRandom.getRandomHallway(gameData);
        r.addObject(new JunkVendingMachine(r));
        Logger.log("Added vending machine in " + r.getName());

        Room r2;
        do {
             r2 = MyRandom.getRandomHallway(gameData);
        } while (r == r2);
        r2.addObject(new ATM(gameData, r2));

        for (int i = 2; i > 0; i--) {
            r2 = MyRandom.getRandomHallway(gameData);
            r2.addItem(new Rapido());
        }
    }

	private void addRandomItemsToRooms(GameData gameData) {
		while (MyRandom.nextDouble() < 0.5) {
			Room aRoom = MyRandom.sample(gameData.getNonHiddenStationRooms());
			aRoom.addItem(RandomItemManager.getRandomSpawnItem());
			Logger.log("Added a suprise in " + aRoom.getName());
		}
	}


	public static String getAvailableJobs() {
		StringBuffer res = new StringBuffer();
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		Collections.sort(list, new CharacterSpeedComparator());
        String delim = "<player-data-part>";
		for (GameCharacter gc : list) {
			res.append("p" + gc.getBaseName() + delim + ((CrewCharacter)gc).getJobDescription() + delim);
		}
		res.append("u" + "Artificial Intelligence" + delim + AICharacter.getJobDescription() + delim);
		for (String gc : availableAntagonists()) {
			res.append("a" + gc + delim + getAntagonistDescription(gc) + delim);
		}

        return res.toString();
	}

	public static List<String> availableAntagonists() {
		return List.of("Traitor", "Host", "Operative", "Changeling", "Rogue AI", "Wizard");
	}


	private static String getAntagonistDescription(String gc) {
		Map<String, String> map = new HashMap<>();
		map.put("Traitor", JobDescriptionMaker.getTraitorDescription());
		map.put("Host", HostCharacter.getAntagonistDescription());
		map.put("Operative", OperativeCharacter.getAntagonistDescription());
		map.put("Changeling", ChangelingCharacter.getAntagonistDescription());
		map.put("Rogue AI", AICharacter.getAntagonistDescription());
		map.put("Wizard", WizardCharacter.getAntagonistDescription());
		return map.get(gc);
	}


	public static Set<String> getAvailCharsAsStrings() {
		return availableChars().keySet();
	}

	public static String getAvailableModesAsString() {
		StringBuffer res = new StringBuffer();
		for (String s : knownModes) {
			if (!s.equals(knownModes[0])) {
				res.append("<player-data-part>");
			}
			res.append(s);

		}
		return res.toString();
	}


	public void triggerEvents(GameData gameData) {
		spawnParasites(gameData);

		for (Event e : events.values()) {
			e.apply(gameData);
		}

		triggerModeSpecificEvents(gameData);
	}

	protected void triggerModeSpecificEvents(GameData gameData) { }


	protected void spawnParasites(GameData gameData) { 
		//possibly spawn some parasites
		double PARASITE_SPAWN_CHANCE = 0.33;

		if (MyRandom.nextDouble() < PARASITE_SPAWN_CHANCE) {
			List<Room> parasiteRooms = new ArrayList<>();
			parasiteRooms.addAll(gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME));
			parasiteRooms.removeIf((Room r) -> r instanceof DecorativeRoom || !r.isPartOfStation());
			Room randomRoom = MyRandom.sample(parasiteRooms);
			NPC parasite = new ParasiteNPC(randomRoom);
            Logger.log("Added parasite in " + randomRoom.getName());
			gameData.addNPC(parasite);
			allParasites.add(parasite);
		}
	}

	public Map<String, Event> getEvents() {
		return events;
	}

	protected List<NPC> getAllParasites() {
		return allParasites;
	}

	public void addFire(Room position) {
		OngoingEvent fires = (OngoingEvent)events.get("fires");
		fires.startNewEvent(position);
	}


    public void addToDefusedBombs(int i) {
        defusedBombs += i;
    }

    public void setMaxBombChain(int maxBombChain) {
        this.maxBombChain = maxBombChain;
    }

    public int getMaxChain() {
        return maxBombChain;
    }

    public int getBombsDefused() {
        return defusedBombs;
    }


    public List<String> getMiscHappenings() {
        return miscHappenings;
    }


    public boolean aiIsPlayer() {
        return aIPlayer != null;
    }

    public void lateJoiningPlayer(Player newPlayer, GameData gameData, boolean spectator) {

		if (spectator) {
			newPlayer.setCharacter(new SpectatorCharacter(gameData));
			newPlayer.moveIntoRoom(newPlayer.getCharacter().getStartingRoom(gameData));
			return;
		}

		GameCharacter chAr;
		if (remainingChars.size() == 0) {
			chAr = MyRandom.sample(VisitorCharacter.getSubtypes());
			newPlayer.setCharacter(chAr);
		} else {
			chAr = MyRandom.sample(remainingChars);
			setPlayersCharacter(newPlayer, chAr, remainingChars);
		}

		gameModeSpecificSetupForLateJoiner(newPlayer, gameData);

		if (lateJoiningShuttle == null) {
			lateJoiningShuttle = new LateJoiningShuttle(gameData);
		}
		DockingPoint dp = null;
		if (!lateJoiningShuttle.isDocked()) {
			dp = findBestArrivalDockingPoint(gameData);
			if (dp != null) {
				try {
					gameData.getMap().addRoom(lateJoiningShuttle, GameMap.STATION_LEVEL_NAME,
							gameData.getMap().getAreaForRoom(GameMap.STATION_LEVEL_NAME, dp.getRoom()));
				} catch (NoSuchThingException e) {
					e.printStackTrace();
				}
				lateJoiningShuttle.dockYourself(gameData, dp);
				gameData.addEvent(new LateJoiningShuttle.UndockingEvent(lateJoiningShuttle, gameData.getRound()));
			}
		}
		try {
			Room arrivalRoom = findArrivalRoom(gameData, dp);
			newPlayer.moveIntoRoom(arrivalRoom);
		} catch (NoSuchThingException e) {
			Logger.log(Logger.CRITICAL, "No Shuttle gate to put new player!");
			newPlayer.moveIntoRoom(MyRandom.sample(gameData.getNonHiddenStationRooms()));
		}

		List<GameItem> startingItems = newPlayer.getCharacter().getStartingItems();
		Logger.log("Giving starting items to " + newPlayer.getPublicName());
		newPlayer.giveStartingItemsToSelf();
		bank.addAccount(newPlayer);

		addStartingMessage(gameData, newPlayer);
		informOnStation(gameData, newPlayer);
	}

	private Room findArrivalRoom(GameData gameData, DockingPoint dp) throws NoSuchThingException {
		if (dp == null || dp.getRoom().getNeighborList().isEmpty()) {
			return gameData.getRoom("Shuttle Gate");
		}
		return dp.getRoom().getNeighborList().get(0);
	}

	private DockingPoint findBestArrivalDockingPoint(GameData gameData) {
		List<DockingPoint> others = new ArrayList<>();
		for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
			if (dp.getName().toLowerCase().contains("airlock 2")) {
				return dp;
			}
			others.add(dp);
		}
		if (!others.isEmpty()) {
			return MyRandom.sample(others);
		}
		return null;
	}

	private void informOnStation(GameData gameData, Player newPlayer) {
        final String message;
        try {
            message = gameData.getClidForPlayer(newPlayer) + " the " + newPlayer.getCharacter().getBaseName() +
                    " has arrived late for " +
            (newPlayer.getCharacter().getGender().equals("man")?"his":"her") + " shift.";
            if (gameData.getGameState() == GameState.MOVEMENT) {
                Event.runOnceAtEndOfMovement(gameData, (GameData gd) -> {try {
                    gameData.findObjectOfType(AIConsole.class).informOnStation(message, gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }});
            } else {
                gameData.findObjectOfType(AIConsole.class).informOnStation(message, gameData);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    public PersonalGoalAssigner getTasks() {
        return tasks;
    }

    public Actor getCaptain() {
        return capCl;
    }

    public static boolean isAMode(String set) {
        for (String s : knownModes) {
            if (s.equals(set)) {
                return true;
            }
        }
        return false;
    }


	public List<Pair<Sprite, String>> getSpectatorContent(GameData gameData, Actor whosAsking) {

        return new ArrayList<>();
    }


    public String getDescriptionHTML() {
		return "<table><tr><td>" +
				"<img width='170' height='110' src='" + getImageURL() + "'</img>" +
				"</td><td>" +
				getModeDescription() +
				"</td></tr></table>";
	}

	public Bank getBank() {
		return bank;
	}

	protected NPC makeDecoy(Player p, GameData gameData) {
		NPC npc = new HumanNPC(p.getCharacter(), p.getCharacter().getStartingRoom(gameData));
		p.getCharacter().setActor(npc);
		gameData.addNPC(npc);
		return npc;
	}


}
