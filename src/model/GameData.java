package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.MyRandom;
import util.MyStrings;
import util.Pair;
import model.actions.Action;
import model.actions.DoNothingAction;
import model.actions.SpeedComparator;
import model.events.Event;
import model.items.GameItem;
import model.map.GameMap;
import model.map.MapBuilder;
import model.map.Room;
import model.modes.GameMode;
import model.modes.GameModeFactory;
import model.npcs.NPC;
import model.objects.GameObject;



/**
 * @author erini02
 * Class for representing the Game's data, i.e. both the information about the
 * players (the clients), the world (the rooms of the station) and what state
 * the game is in. The game also has a "mode".
 */
public class GameData {
	private final Date startingTime = new Date();
	// these fields should persist between games
	private HashMap<String, Player> players = new HashMap<>();
	private GameState gameState             = GameState.PRE_GAME;

	// These should be set up anew for each game.
	private List<NPC> npcs;
	private GameMode gameMode;
	private int round = 0;
	private int noOfRounds = 20;
	private List<Pair<Actor, Action>> lateActions;
	// Map must be built before first game, client needs it.
	private GameMap map                     = MapBuilder.createMap();
	private String selectedMode = "Secret";
	private List<Event> events = new ArrayList<>();
	private List<Event> moveEvents = new ArrayList<>();

	
	public GameData() {
		
	}
	
	/**
	 * Gets all the clients as a Map of clients as keys and booleans as values.
	 * True indicating that the client is ready, false => not ready.
	 * @return the map
	 */
	public Map<String, Boolean> getClientsAsMap() {
		HashMap<String, Boolean> hm = new HashMap<>();
		for (Entry<String, Player> e : players.entrySet()) {
			hm.put(e.getKey(), e.getValue().isReady());
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
	public List<Room> getRooms() {
		List<Room> list = new ArrayList<>();
		list.addAll(getAllRooms());
		list.remove(map.getRoom("Nuclear Ship"));
		return list;
	}
	
	
	/**
	 * Prints the clients (and if they are ready or not) to the console
	 */
	public void printClients() {
		for (Entry<String, Boolean> s : getClientsAsMap().entrySet()) {
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
		return players.get(clid);
	}


	/**
	 * Creates a new client and adds it to the game.
	 * The client gets a unique ID called a CLID.
	 * @return the CLID of the new client.
	 */
	public String createNewClient(String rest) {
		String clid = rest;
		int i = 2;
		if (rest.equals("")) {
			clid = getRandomClid();
		}
		
		if (getClientsAsMap().containsKey(clid)) {
			throw new IllegalStateException("THAT USER NAME ALREADY TAKEN!");
		}
		
		players.put(clid, new Player(this));
		return clid;
	}


	private String getRandomClid() {
		String clid;
		do {
			clid = new String("CL" + (MyRandom.nextInt(900)+100));
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
				p.getPosition().removePlayer(p);
			}
		}
		players.remove(otherPlayer);
	}


	/**
	 * Finds a certain client and sets its ready status.
	 * @param clid, the client whose status to set.
	 * @param equals, the new ready status for the client.
	 */
	public void setCientReady(String clid, boolean equals) {
		players.get(clid).setReady(equals);
		if (allClientsReadyOrDead()) {
			increaseGameState();	
		}
		
	}

	/**
	 * Creates a string with the player's data and selectable rooms.
	 * @param clid the player to create the data for.
	 * @return a string representation of the player data.
	 */
	public String createPlayerMovementData(String clid) {
		Player cl = players.get(clid);
		String result =  Arrays.toString(cl.getSelectableLocations(this)) + ":" + createBasicPlayerData(cl);
		return result;
	}


	/**
	 * Create a string with the player's data and available actions.
	 * @param clid the player to create the data for.
	 * @return a string representation of the player data.
	 */
	public String createPlayerActionData(String clid) {
		Player cl = players.get(clid);
		String result = cl.getActionListString(this) + ":" + createBasicPlayerData(cl);
		return result;
	}

	
	/**
	 * Gets the clients as a collection.
	 * @return the collection of clients.
	 */
	public Collection<Player> getPlayersAsList() {
		return players.values();
	}

	
	private void allClearReady() {
		for (Player c : players.values()) {
			c.setReady(false);
		}
	}


	private boolean allClientsReadyOrDead() {
		for (Player c : players.values()) {
			if (!c.isReady()) {
				if (gameState == GameState.PRE_GAME) {
					return false;
				} else if (!c.isDead()) {
					return false;
				}
			}
		}		
		return true;
	}


	private void increaseGameState() {
		if (gameState == GameState.PRE_GAME) {
			gameState = GameState.MOVEMENT;
			doSetup();
			allClearReady();
			
		} else if (gameState == GameState.MOVEMENT) {
			moveAllPlayers();
			moveAllNPCs();
			
			
			allResetActionStrings();
			allClearLastTurn();
			runMovementEvents();
			
			gameMode.setStartingLastTurnInfo();
			allClearReady();
			clearAllDeadNPCs();
			gameState = GameState.ACTIONS;
			
		} else if (gameState == GameState.ACTIONS) {
			executeAllActions();
			
			runEvents();
			executeLateAction();
			informPlayersOfRoomHappenings();
			if (gameMode.gameOver(this)) {
				gameState = GameState.PRE_GAME;
				//round = 0; <-- Dont do this, if you do
				//               the gamemode wont be able to
				//               se that the game is over.
			} else {
				gameState = GameState.MOVEMENT;
				round = round + 1;
			}
			allClearReady();
		}
		
	}




	private void runEvents() {
		Event e = null;
		for (Iterator<Event> it = events.iterator() ; it.hasNext(); ) {
			e = it.next();
			e.apply(this);
			if (e.shouldBeRemoved(this)) {
				System.out.println("Removing an event.");
				it.remove();
			}
		}
		gameMode.triggerEvents(this);
	}

	private void runMovementEvents() {
		Event e = null;
		for (Iterator<Event> it = moveEvents .iterator(); it.hasNext(); ) {
			e = it.next();
			e.apply(this);
			if (e.shouldBeRemoved(this)) {
				it.remove();
			}
		}
	}

	
	private void doSetup() {
		this.map = MapBuilder.createMap();
		for (Player p : getPlayersAsList()) {
			p.prepForNewGame();
		}
		this.events = new ArrayList<>();
		this.moveEvents = new ArrayList<>();
		this.lateActions = new ArrayList<>();
		this.npcs = new ArrayList<>();
		this.gameMode = GameModeFactory.create(selectedMode);
		this.round = 1;
		gameMode.setup(this);
	}

	


	private void clearAllDeadNPCs() {
		Iterator<NPC> it = npcs.iterator();
		
		while (it.hasNext()) {
			NPC npc = it.next();
			if (npc.shouldBeCleanedUp() && npc.isDead()) {
				it.remove();
				npc.getPosition().removeNPC(npc);
			}
		}
	}

	public boolean isAllDead() {
		for (Player cl : players.values()) {
			if (!cl.isDead()) {
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
			cl.setNextAction(new DoNothingAction());
		}
	}

	private void executeAllActions() {
		List<Actor> actionPerfs = new ArrayList<>();
		actionPerfs.addAll(this.players.values());
		actionPerfs.addAll(this.npcs);
		
		Collections.sort(actionPerfs, new SpeedComparator());
		for (Actor ap : actionPerfs) {
			System.out.println("Action for " + ap.getPublicName());
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


	private void moveAllPlayers() {
		for (Player cl : players.values()) {
			cl.moveIntoRoom(map.getRoomForID(cl.getNextMove()));
		}
	}
	
	private void moveAllNPCs() {
		for (NPC npc : npcs) {
			System.out.println("Moving NPC " + npc.getName());
			npc.moveAccordingToBehavior();
		}
	}


	private String createBasicPlayerData(Player cl) {		
		String result = cl.getCharacterRealName() + 
				       ":" + cl.getPosition().getID() + 
					   ":" + cl.getCurrentHealth() +
					   ":" + String.format("%1$.1f", cl.getCharacter().getTotalWeight()) +
					   ":" + cl.getSuit() +
					   ":" + MyStrings.join(cl.getItemsAsFullNameList(), "|") + 
					   ":" + MyStrings.join(cl.getRoomInfo(), "|") + 
					   ":" + MyStrings.join(cl.getLastTurnInfo(), "|");
		return result;	
		
	}


	public Room getRoomForId(int ID) {
		return map.getRoomForID(ID);
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

	public Room getRoom(String string) {
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

	public String getPollData() {
		return makeStringFromReadyClients()+ ":" + getGameState().val + ":" + getRound() +
				 ":" + getNoOfRounds() + ":" + getSelectedMode() + ":" + GameMode.getAvailableModesAsString();
	}

	public void setSettings(String rest) {
		if (gameState == GameState.PRE_GAME) { //can only change settings in pre game
			System.out.println("Setting new settings: " + rest);
			String[] sets = rest.substring(1).split(":");
			try {
				setNumberOfRounds(Integer.parseInt(sets[0]));
				selectedMode = sets[1];
				//System.out.println("Set new settings");
			} catch (NumberFormatException nfe) {

			}
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

	public Room findRoomForItem(GameItem searchedItem) {
		for (Room r : getAllRooms()) {
			for (GameItem it : r.getItems()) {
				if (it == searchedItem) {
					return r;
				}
			}
		}

		return null;
	}

	/**
	 * Gets all rooms that exist in the game.
	 * @return
	 */
	public List<Room> getAllRooms() {
		return map.getRooms();
	}

	public Actor findActorForItem(GameItem searchedItem) {
		for (Actor a : getActors()) {
			for (GameItem it : a.getItems()) {
				if (searchedItem == it) {
					return a;
				}
			}
		}
		return null;
	}

	public GameMap getMap() {
		return map;
	}

	public void addMovementEvent(Event event) {
		moveEvents.add(event);
	}

	/**
	 * Gets all game objects in all the rooms.
	 * @return all objects as a list.
	 */
	public List<GameObject> getObjects() {
		List<GameObject> obj = new ArrayList<>();
		for (Room r : getAllRooms()) {
			obj.addAll(r.getObjects());
		}

		return obj;
	}




}
