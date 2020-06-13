package model;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import comm.chat.ChatMessages;
import graphics.OverlaySprite;
import main.SS13Client;
import model.actions.QuickAction;
import model.actions.manager.ActionManager;
import model.actions.objectactions.JumpStationAction;
import model.characters.GameCharacterLambda;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.special.SpectatorCharacter;
import model.events.timed.TimedEvent;
import model.items.NoSuchThingException;
import model.map.rooms.DecorativeRoom;
import model.modes.GameCouldNotBeStartedException;
import model.modes.SecretGameMode;
import model.objects.general.ContainerObject;

import model.plebOS.ComputerSystem;
import util.*;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SpeedComparator;
import model.events.Event;
import model.items.general.GameItem;
import model.map.GameMap;
import model.map.builders.MapBuilder;
import model.map.rooms.Room;
import model.modes.GameMode;
import model.modes.GameModeFactory;
import model.npcs.NPC;
import model.objects.general.GameObject;



/**
 * @author erini02
 * Class for representing the Game's data, i.e. both the information about the
 * players (the clients), the world (the rooms of the station) and what state
 * the game is in. The game also has a "mode".
 */
public class GameData implements Serializable {

	private final Date startingTime = new Date();
    private final boolean recover;
    // these fields should persist between games
	private HashMap<String, Player> players = new HashMap<>();
	private GameState gameState             = GameState.PRE_GAME;

	// These should be set up anew for each game.
	private List<NPC> npcs = new ArrayList<>();
	private GameMode gameMode;
	private int round = 0;
	private int noOfRounds = 40;
	private List<Pair<Actor, Action>> lateActions;
	// Map must be built before first game, client needs it.
	private GameMap map;
	private String selectedMode = "Secret";
	private List<Event> events = new ArrayList<>();
	private List<Event> moveEvents = new ArrayList<>();
	private List<TimedEvent> timedEvents = new ArrayList<>();
    private boolean runningEvents;
    private ChatMessages chatMessages = new ChatMessages();
    private List<String> lostMessages = new ArrayList<>();
    private ComputerSystem computerSystem = new ComputerSystem();
	private long lastTimeCount = 0;
	private long lastRoundTimeCount = 0;
	private long roundTimeLimitS = 90;


	public GameData(boolean recover) {
        map = MapBuilder.createMap(this);
        this.recover = recover;
	}
	
	/**
	 * Gets all the clients as a Map of clients as keys and booleans as values.
	 * True indicating that the client is ready, false => not ready.
	 * @return the map
	 */
	public Map<String, BooleanWrapper> getClientsAsMap() {
		HashMap<String, BooleanWrapper> hm = new HashMap<>();
		for (Entry<String, Player> e : players.entrySet()) {
            if ((e.getValue().getCharacter() != null && e.getValue().getInnermostCharacter() instanceof SpectatorCharacter) &&
                    gameState != GameState.PRE_GAME) {
                 hm.put(e.getKey(), new BooleanWrapper(e.getValue().isReady(), true));
            } else {
                hm.put(e.getKey(),  new BooleanWrapper(e.getValue().isReady(), false));
            }
		}
		return hm;
	}
	

	/**
	 * Makes a string representation of all the clients and whether they are ready or not.
	 * @return the string representation
	 */
	public String makeStringFromReadyClients() {
		return getClientsAsMap().toString();
	}


	/**
	 * Gets all the rooms on SS13 as a list.
	 * @return the list of rooms.
	 */
	public List<Room> getNonHiddenStationRooms() {
		Set<Room> set = new HashSet<>();
		set.addAll(getMap().getRoomsForLevel("ss13"));
        set.removeIf((Room r) -> !r.isPartOfStation() || r.isHidden());

        List<Room> list = new ArrayList<>();
        list.addAll(set);
        Collections.sort(list, Comparator.comparing(Room::getName));
		return list;
	}
	
	
	/**
	 * Prints the clients (and if they are ready or not) to the console
	 */
	public void printClients() {
		for (Entry<String, BooleanWrapper> s : getClientsAsMap().entrySet()) {
			System.out.println("  " + s.getKey() + " " + s.getValue());
		}
	}


	/**
	 * Gets the game state.
	 * 1 = pre-game
	 * 2 = movement
	 * 3 = actions
	 * @return the game state.
	 */
	public GameState getGameState() {
		return gameState;
	}


	/**
	 * Gets a client for a certain clid, or null if no such client was found.
	 * @param clid the clid of the client we are looking for.
	 * @return the searched for client.
	 */
	public Player getPlayerForClid(String clid) {

        if (players.get(clid) == null) {
            Logger.log(Logger.CRITICAL, "Finding player for clid was nulL!: " + clid);
        }
		return players.get(clid);
	}

    public String getClidForPlayer(Player c) throws NoSuchThingException {
        for (String id : players.keySet()) {
            if (players.get(id) == c) {
                return id;
            }
        }
        throw new NoSuchThingException("No ID for that player!");
    }


	/**
	 * Creates a new client and adds it to the game.
	 * The client gets a unique ID called a CLID.
	 * @return the CLID of the new client.
	 */
	public String createNewClient(String rest, boolean spectator) {
		String clid = rest;
		if (rest.equals("")) {
			clid = getRandomClid();
		}
		
		Player newPlayer = addNewPlayer(clid);
        if (spectator) {
            newPlayer.getSettings().set(PlayerSettings.MAKE_ME_A_SPECTATOR, true);
        }
        //newPlayer.getSoundQueue().add(Sound.YOU_JUST_JOINED);

        if (getGameState() != GameState.PRE_GAME) { // already started
            newPlayer.prepForNewGame();
            newPlayer.setNextAction(new DoNothingAction());
            gameMode.lateJoiningPlayer(newPlayer, this, spectator);
        }

        getChat().serverSay(clid + " has joined.");

		return clid;
	}

    public Player addNewPlayer(String clid) {
        if (getClientsAsMap().containsKey(clid)) {
            throw new IllegalStateException("THAT USER NAME ALREADY TAKEN!");
        }

        Player newPlayer = new Player(this);
        players.put(clid, newPlayer);
        return newPlayer;
    }


    private String getRandomClid() {
		String clid;
		do {
            clid = MyRandom.getRandomName();
		} while (getClientsAsMap().containsKey(clid));
		return clid;
	}

	/**
	 * Removes a client from the game.
	 * @param otherPlayer, the client to be removed.
	 */
	public void removeClient(String otherPlayer) {
		Player p = players.get(otherPlayer);
		if (p == null) {
			return; // player does not exist...
		}
		if (p.getCharacter() != null) {
			if (p.getPosition() != null) {
                try {
                    p.getPosition().removePlayer(p);
                } catch (NoSuchThingException e) {
                    Logger.log(Logger.CRITICAL, "Tried to remove a person from room that wasn't there!");
                }
            }
		}
		players.remove(otherPlayer);
        getChat().serverSay(otherPlayer + " has been kicked.");
	}


	/**
	 * Finds a certain client and sets its ready status.
	 * @param clid, the client whose status to set.
	 * @param equals, the new ready status for the client.
	 */
	public void setPlayerReady(String clid, boolean equals) {
		players.get(clid).setReady(equals);
		if (allClientsReadyOrPassive()) {
			increaseGameState();
			if (getGameState() == GameState.MOVEMENT) {
			    increaseGameState();
            }
		}
	}

	public void unreadyThePlayer(Player performingClient) {
		try {
			setPlayerReady(getClidForPlayer(performingClient), false);
		} catch (NoSuchThingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a string with the player's data and selectable rooms.
	 * @param clid the player to create the data for.
	 * @return a string representation of the player data.
	 */
	public String createPlayerMovementData(String clid) {
		Player cl = players.get(clid);
		String result =  Arrays.toString(cl.getSelectableLocations(this)); // + "<player-data-part>" + createBasicPlayerData(cl);
		return result;
	}


	/**
	 * Create a string with the player's data and available actions.
	 * @param clid the player to create the data for.
	 * @return a string representation of the player data.
	 */
	public String createPlayerActionData(String clid) {
		Player cl = players.get(clid);
		String result = cl.getActionListString(this) + "<player-data-part>" + createBasicPlayerData(cl);
		return result;
	}

	
	/**
	 * Gets the clients as a collection.
	 * @return the collection of clients.
	 */
	public Collection<Player> getPlayersAsList() {
		return players.values();
	}

	
	public void allClearReady() {
		for (Player c : players.values()) {
			c.setReady(false);
		}
	}


	private boolean allClientsReadyOrPassive() {
		for (Player c : players.values()) {
			if (!c.isReady()) {
                if (!c.getSettings().get(PlayerSettings.MAKE_ME_A_SPECTATOR)) {
                    if (gameState == GameState.PRE_GAME) {
                        return false;
                    } else if (!c.isPassive()) {
                        return false;
                    }
                }
            }
		}		
		return true;
	}


	private void increaseGameState() {
		if (gameState == GameState.PRE_GAME) {
            try {
                doSetup();
				allClearReady();
				gameState = GameState.MOVEMENT;
            } catch (GameCouldNotBeStartedException gcnbese) {
                chatMessages.serverSay("Game could not be started! " +gcnbese.getMessage());
                Logger.log("Game could not be started! " + gcnbese.getMessage() + " gamestate remains at " + gameState.name);
                unsetup();
            }

		} else if (gameState == GameState.MOVEMENT) {
			lastRoundTimeCount = System.currentTimeMillis();
            forEachCharacter(((GameCharacter ch) -> ch.doBeforeMovement(this)));
            MovementData moveData = new MovementData(this);
			moveAllNPCs();
			moveAllPlayers(moveData);
			allResetActionStrings();

            moveData.informPlayersOfMovements(this);
			runMovementEvents();

			if (getRound() == 1) {
                gameMode.setStartingLastTurnInfo();
            }
            sendLostMessages();
            forEachCharacter(((GameCharacter ch) -> ch.doAfterMovement(this)));
			 allClearReady();
			//clearAllDeadNPCs();
			gameState = GameState.ACTIONS;
            try {
                if (recover) {
                    GameRecovery.saveData(this);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


		} else if (gameState == GameState.ACTIONS) {
            allClearLastTurn();
			allClearExtraEffects();
			executeAllActions();
			ActionManager.clearMap();
			forEachCharacter((GameCharacter ch) -> ch.doAfterActions(this));
			runEvents();
			executeLateAction();
			informPlayersOfRoomHappenings();
			forEachCharacter((GameCharacter ch) -> ch.doAtEndOfTurn(this));
			if (gameMode.gameOver(this)) {
                getChat().serverSay("Game is over!");
                this.getGameMode().doWhenGameOver(this);
				gameState = GameState.PRE_GAME;
				//round = 0; <-- Don't do this, if you do
				//               the gamemode wont be able to
				//               se that the game is over.
			} else {
				gameState = GameState.MOVEMENT;
				round = round + 1;
                getChat().serverSay("Starting round " + round + " (of " + noOfRounds + ")");

			}
        	allClearReady();
		}

	}

	private void allClearExtraEffects() {
		for (Player p : getPlayersAsList()) {
			p.clearExtraEffects();
		}
	}


	private void cleanChars() {
        for (Player p : getPlayersAsList()) {
            p.setCharacter(null);
        }
    }


    private void forEachCharacter(GameCharacterLambda o) {
        for (Actor a : getActors()) {
            o.doAction(a.getCharacter());
        }
    }


    private void runEvents() {
        this.runningEvents = true;
        gameMode.triggerEvents(this);

        List<Event> eventsToRemove = new ArrayList<>();
		List<Event> eventsToExecute = new ArrayList<>();
		eventsToExecute.addAll(events);
		
		for (Event e : eventsToExecute) {
		    //Logger.log("Running event " + e.toString());
			e.apply(this);
			if (e.shouldBeRemoved(this)) {
				eventsToRemove.add(e);
			}
		}
        Logger.log("Events done!");
		
		for (Event e2 : eventsToRemove) {
            Logger.log("Possibly removing event " + e2.toString());
			events.remove(e2);
		}
	    this.runningEvents = false;
	}

	private void runMovementEvents() {
		for (Iterator<Event> it = moveEvents .iterator(); it.hasNext(); ) {
			Event e = it.next();
			e.apply(this);
			if (e.shouldBeRemoved(this)) {
				it.remove();
			}
		}
	}

	
	private void doSetup() {
		Logger.log("Doing setup:");
        this.npcs = new ArrayList<>();
        this.events = new ArrayList<>();
        this.moveEvents = new ArrayList<>();
        this.lateActions = new ArrayList<>();
		this.map = MapBuilder.createMap(this);
		map.runSetupForAllRooms(this);
		Logger.log("Map built");
		for (Player p : getPlayersAsList()) {
			p.prepForNewGame();
		}
		Logger.log("Players prepped");

		this.gameMode = GameModeFactory.create(selectedMode);
		Logger.log("Got game mode from factory.");
		this.round = 1;
        this.runningEvents = false;
		gameMode.setup(this);
		copyCharacterStyles();
		characterCreationSetup();
		Logger.log("Game mode set-upped!");
	    computerSystem.setUpGame(this);
		Logger.log("Computer System setupped.");
        getChat().serverSay("New game started, mode: " + selectedMode);
	}


	private void unsetup() {
		Logger.log("Doing unsetup");
		this.npcs = new ArrayList<>();
		this.events = new ArrayList<>();
		this.moveEvents = new ArrayList<>();
		this.lateActions = new ArrayList<>();
		cleanChars();
		gameMode = null;
		this.round = 0;
	}



	private void characterCreationSetup() {
		for (Player p : players.values()) {
			p.getCharacterCreation().doAtGameStart(this, p);
		}
	}

	private void copyCharacterStyles() {
		for (Player p : players.values()) {
			p.setCharacterStyle();
		}

	}





    private void clearAllDeadNPCs() {
		Iterator<NPC> it = npcs.iterator();
		
		while (it.hasNext()) {
			NPC npc = it.next();
			if (npc.shouldBeCleanedUp() && npc.isDead()) {
				it.remove();
                try {
                    npc.getPosition().removeNPC(npc);
                } catch (NoSuchThingException e) {
                    Logger.log(Logger.CRITICAL, "Tried removing dead npc but it was not found in room.");
                }
            }
		}
	}

	public boolean isAllDead() {
		for (Player cl : players.values()) {
			if (!cl.isDead() &&
                    !cl.getCharacter().checkInstance((GameCharacter a) -> a instanceof AICharacter ||
                            a instanceof SpectatorCharacter))  {
				return false;
			}
		}
		
		return true;
	}

	private void allClearLastTurn() {
		for (Player cl : players.values()) {
            cl.clearLastTurnInfo();
		}
		for (Room r : map.getRooms()) {
			r.clearHappenings();
		}
	}

	private void allResetActionStrings() {
		for (Player cl : players.values()) {
			cl.resetAction();
		}
	}

	private void executeAllActions() {
		List<Actor> actionPerfs = new ArrayList<>();
		actionPerfs.addAll(this.players.values());
		actionPerfs.addAll(this.npcs);
		
		Collections.sort(actionPerfs, new SpeedComparator());
		for (Actor ap : actionPerfs) {
			Logger.log("Action for " + ap.getPublicName());
			ap.action(this);
		}
		
	}

	private void executeLateAction() {
		for (Pair<Actor, Action> p : lateActions) {
			if (!p.first.isDead()) {
				p.second.lateExecution(this, p.first);
			}
		}
		lateActions.clear();
	}

	private void informPlayersOfRoomHappenings() {
		for (Room r : map.getRooms()) {
			r.pushHappeningsToPlayers();
		}
	}


	private void moveAllPlayers(MovementData moveData) {
		for (Player cl : players.values()) {
            try {
                if (cl.getNextMove() > 0) {
                	Room target = map.getRoomByID(cl.getNextMove());
                	if (cl.findMoveToAblePositions(this).contains(target) || target == cl.getPosition()) {
						cl.moveIntoRoom(target);
					} else if (!cl.isDead() && !cl.isAI()){
                		Logger.log(cl.getName() + " tried moving to " + target.getName() + ", but can't!");
                		cl.addTolastTurnInfo("Your movement has been blocked! You stayed in your current location.");
                		cl.setNextMove(cl.getPosition().getID());
                		Logger.log("Player is now in " + cl.getPosition().getName() + " and next move is " + cl.getNextMove());
						QuickAction.saveActionPoint(this, cl);
					}
                } else {
                    cl.activateMovementPower(cl.getNextMove(), this, moveData);
                }
            } catch (NoSuchThingException e) {
                //Room did not exist any more
                Logger.log(Logger.CRITICAL, "WHAt, tried moving player to room that did not exist???");
            }
        }
	}
	
	private void moveAllNPCs() {
		for (NPC npc : npcs) {
			Logger.log("Moving NPC " + npc.getName());
			npc.moveAccordingToBehavior();
		}
	}


	private String createBasicPlayerData(Player cl) {
        String delim = "<player-data-part>";

        String overlays =  OverlaySprite.join(cl.getOverlayStrings(this), this);
      	Logger.log("Extra effects are: " + cl.getExtraEffectsAsStrings());
		String result = cl.getCharacterRealName() + 
				       delim + cl.getPosition().getID() +
					   delim + cl.getCurrentHealth() +
					   delim + cl.getWeightString() +
					   delim + MyStrings.join(cl.getEquippedItems(this)) +
					   delim + MyStrings.join(cl.getItemsAsFullNameList(this)) +
					   delim + MyStrings.join(cl.getRoomInfo(this)) +
					   delim + MyStrings.join(cl.getLastTurnInfo()) +
                       delim + overlays +
				       delim + cl.getExtraEffectsAsStrings();
		return result;	
		
	}


	public Room getRoomForId(int ID) throws NoSuchThingException {
		return map.getRoomByID(ID);
	}

	/**
	 * Adds an npc to the game
	 * @param npc, the npc to be added to the game.
	 */
	public void addNPC(NPC npc) {
		npcs.add(npc);
	}

	/**
	 * Gets the round of the game;
	 * @return
	 */
	public int getRound() {
		return round ;
	}

	public Room getRoom(String string) throws NoSuchThingException {
		return map.getRoom(string);
	}

	public String getSummary() {
		return gameMode.getSummary(this);
	}

	public Set<Entry<String, Player>> getPlayersAsEntrySet() {
		return players.entrySet();
	}

	public List<NPC> getNPCs() {
		return npcs;
	}

	public String getAvailableJobs() {
		return GameMode.getAvailableJobs();
	}

	public Set<String> getAvailableJobsAsStrings() {
		return GameMode.getAvailCharsAsStrings();
	}

	public List<Actor> getActors() {
		List<Actor> arr = new ArrayList<>();
		arr.addAll(getPlayersAsList());
		arr.addAll(getNPCs());
		return arr;
	}

	public void executeAtEndOfRound(Actor performingClient,
			Action act) {
		lateActions.add(new Pair<Actor, Action>(performingClient, act));
	}

	public int getNoOfRounds() {
		return noOfRounds;
	}

	public String getSelectedMode() {
		return selectedMode ;
	}

	public String getPollData(String clid) {
        String del = "<player-data-part>";
        String nextAct = "Do Nothing";
        if (getPlayerForClid(clid).getNextAction() != null) {
        	nextAct = getPlayerForClid(clid).getNextAction().getName();
		}

		String ambientSound = "nothing";
        if (gameState != GameState.PRE_GAME) {
        	ambientSound = getPlayerForClid(clid).getAmbientSound();
		}

		return makeStringFromReadyClients()+ del + getGameState().val + del +
                getRound() + del + getNoOfRounds() + del + chatMessages.getLastMessageIndex(getPlayerForClid(clid)) + del +
                getPlayerForClid(clid).getFancyFrame().getState() + del +
                getSelectedMode() + del + SS13Client.CLIENT_VERSION_STRING + del +
                getRoundTimeLimitS() + del + getRoundTimeLeft() + del +
				getPlayerForClid(clid).getSoundQueue().getCurrentIndex() + del +
				nextAct + del +
				getPlayerForClid(clid).getDataState() + del +
				ambientSound;
	}

    private long getRoundTimeLeft() {
        return (getRoundTimeLimitS()*1000) - (lastTimeCount - lastRoundTimeCount);
    }

    public void setSettings(String rest, Player pl) {
        //String str = "<player-data-part>";
        Logger.log("Setting new settings: " + rest);
        String del = "<player-data-part>";
        String[] sets = rest.substring(1).split(del);
		if (gameState == GameState.PRE_GAME) { //can only change settings in pre game

			try {
			    int newRounds = Integer.parseInt(sets[0]);
			    if (getNoOfRounds() != newRounds) {
                    setNumberOfRounds(newRounds);
                    getChat().serverSay(getClidForPlayer(pl) + " set round limit to " + newRounds + ".");
                }
                if (GameMode.isAMode(sets[1])) {
                    selectedMode = sets[1];
                    getChat().serverSay(getClidForPlayer(pl) + " set mode to " + selectedMode + ".");
                }
                if (sets.length > 2) {
                    long newTimeLimit = Long.parseLong(sets[2]);
                    if (getRoundTimeLimitS() != newTimeLimit) {
                        roundTimeLimitS = newTimeLimit;
                        getChat().serverSay(getClidForPlayer(pl) + " set round time limit to " + roundTimeLimitS + ".");
                    }
                }
			} catch (NumberFormatException nfe) {

			} catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } else {
		    getChat().serverSay("You cannot change any server settings while the game is running.", pl);
        }

	}
	
	public int getNumberOfRounds() {
		return noOfRounds;
	}
	
	public void setNumberOfRounds(int i) {
		noOfRounds = i;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	
	/**
	 * @return the info to be showed to the client when looking for available.
	 * ss13 servers. Fields should be separated with ':'
	 */
	public String getInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm EEE, MMM d, yyyy");
		return getPlayersAsList().size() + ":" + gameState.toInfo() + ":" + this.getRound() + ":" + sdf.format(getStartingTime());
	}

	private Date getStartingTime() {
		return startingTime;

	}

	public void addEvent(Event event) {
		events.add(event);
	}


	public void addTimedEvent(TimedEvent timedEvent) {
		timedEvents.add(timedEvent);
	}

	public Room findRoomForItem(GameItem searchedItem) throws NoSuchThingException {
		for (Room r : getAllRooms()) {
			for (GameItem it : r.getItems()) {
				if (it == searchedItem) {
					return r;
				}
			}
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof ContainerObject) {
                    if (((ContainerObject)ob).getInventory().contains(searchedItem)) {
                        return r;
                    }
                }
            }
		}
        Room r = searchedItem.getPosition();
        if (r != null) {
            return r;
        }
		throw new NoSuchThingException("Room for searched item " + searchedItem.getBaseName() + " not found");
	}

	/**
	 * Gets all rooms that exist in the game.
	 * @return
	 */
	public List<Room> getAllRooms() {
		return map.getRooms();
	}

	public Actor findActorForItem(GameItem searchedItem) throws NoSuchThingException {
		for (Actor a : getActors()) {
			for (GameItem it : a.getItems()) {
				if (searchedItem == it) {
					return a;
				}
			}
		}
        throw new NoSuchThingException("Could not find actor for searched item " + searchedItem.getBaseName());
	}

	public GameMap getMap() {
		return map;
	}

	public void addMovementEvent(Event event) {
		moveEvents.add(event);
	}

	/**
	 * Gets all game objects in all the rooms.
     * @Deprecated, use getObjectsForLevel instead or getAllObjects
	 * @return all objects as a list.
	 */
    @Deprecated
	public List<GameObject> getObjects() {
		Set<GameObject> set = new HashSet<>();
		List<GameObject> obj = new ArrayList<>();
		for (Room r : getAllRooms()) {
			set.addAll(r.getObjects());
		}
		obj.addAll(set);
		return obj;
	}

    public List<GameObject> getAllObjects() {
        return getObjects();
    }

    public List<GameObject> getObjectsForLevel(String level) {
        List<GameObject> list = new ArrayList<>();
        //Logger.log("Getting objects for level: Rooms:");
        for (Room room : getMap().getRoomsForLevel(level)) {
            //Logger.log("     " + room.getName());
            for (GameObject ob : room.getObjects()) {
                list.add(ob);
            }
        }
        Logger.log("End getting objects for level");
        return list;
    }


    public boolean isRunningEvents() {
        return runningEvents;
    }

    public <T extends GameObject> T  findObjectOfType(Class<T> className) throws NoSuchThingException {
        for (Room r : getNonHiddenStationRooms()) {
            for (GameObject obj : r.getObjects()) {
                if (obj.getClass() == className) {
                    return (T)obj;
                } else if (obj.isOfType(className)) {
                    return (T)obj.getTrueObject();
                }

            }
        }
        throw new NoSuchThingException("GameData: Could not find object of type " + className.getName() + "!");
    }


    public void removeEvent(Event ev) {
        events.remove(ev);
    }

    public ChatMessages getChat() {
        return chatMessages;
    }



    private void sendLostMessages() {
        for (Actor a : getActors()) {
            for (String s : lostMessages) {
                a.addTolastTurnInfo(s);
            }
        }
        lostMessages.clear();
    }

    public Collection<? extends Player> getTargetablePlayers() {
        Collection<Player> p = new ArrayList<>();
        p.addAll(getPlayersAsList());
        p.removeIf((Player p2) -> p2.getInnermostCharacter() instanceof AICharacter ||
                p2.getInnermostCharacter() instanceof SpectatorCharacter);
        return p;
    }

    public void addToLostMessages(String lost) {
        lostMessages.add(lost);
    }

    public ComputerSystem getComputerSystem() {
        return computerSystem;
    }

	public String getModesInfo() {
    	final String delim = "<modes-part>";
    	StringBuilder mess = new StringBuilder(noOfRounds + "" + delim + roundTimeLimitS + "" + delim);
    	mess.append(getSelectedMode() + delim);
    	mess.append(GameMode.getAvailableModes().length + delim);
    	for (String s : GameMode.getAvailableModes()) {
    		mess.append(s + delim);
    		if (s.equals("Secret")) {
    		    mess.append(SecretGameMode.getDescriptionHTML() + delim);
            } else {
                GameMode mode = GameModeFactory.create(s);
                mess.append(mode.getDescriptionHTML() + delim);
            }
		}

		for (String mode : SecretGameMode.getModeNames()) {
			mess.append(mode + delim + SecretGameMode.getProbabilityForMode(mode) + delim);
		}
		mess.append("END");

		return mess.toString().replace(delim+"END", "");
	}

	public void doAfterServingClient() {
		long timeElapsed = System.currentTimeMillis() - lastTimeCount;
		List<TimedEvent> removals = new ArrayList<>();
		for (TimedEvent te : timedEvents) {
			te.updateYourself(this, timeElapsed);
			if (te.shouldBeRemoved(this)) {
				removals.add(te);
			}
		}
		timedEvents.removeAll(removals);
		lastTimeCount = System.currentTimeMillis();
		if (lastTimeCount - lastRoundTimeCount > getRoundTimeLimitS()*1000
				&& getGameState() != GameState.PRE_GAME) {
			increaseGameState();
		}
	}

	private long getRoundTimeLimitS() {
		return roundTimeLimitS;
	}

	public void doAfterRecovery() {
    	gameMode.getBank().setReferences(this);
    	lastTimeCount = System.currentTimeMillis();
    	lastRoundTimeCount = lastTimeCount;
    	for (Player p : getPlayersAsList()) {
    		p.getSoundQueue().clear();
		}
	}
}
