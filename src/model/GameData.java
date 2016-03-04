package model;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.MyRandom;
import model.actions.DoNothingAction;
import model.actions.SpeedComparator;
import model.map.GameMap;
import model.map.MapBuilder;
import model.map.Room;
import model.modes.GameMode;
import model.modes.HostGameMode;
import model.npcs.NPC;



/**
 * @author erini02
 * Class for representing the Game's data, i.e. both the information about the
 * players (the clients), the world (the rooms of the station) and what state
 * the game is in. The game also has a "mode".
 */
public class GameData {
	private HashMap<String, Player> players = new HashMap<>();
	private GameMap map = MapBuilder.createMap();
	private List<NPC> npcs = new ArrayList<>();
	private GameState gameState = GameState.PRE_GAME;
	private GameMode gameMode = new HostGameMode();
	private int round = 0;
	
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
	 * Gets all the rooms in the game as a list.
	 * @return the list of rooms.
	 */
	public List<Room> getRooms() {
		return map.getRooms();
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
	public String createNewClient() {
		String clid = null;
		do {
			clid = new String("CL" + (MyRandom.nextInt(900)+100));
		} while (getClientsAsMap().containsKey(clid));
		players.put(clid, new Player(this));
		
		return clid;
	}


	/**
	 * Removes a client from the game.
	 * @param otherPlayer, the client to be removed.
	 */
	public void removeClient(String otherPlayer) {
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
		String result = cl.getActionTreeString(this) + ":" + createBasicPlayerData(cl);
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
			if (!c.isReady() && !c.isDead()) {
				return false;
			}
		}
		
		return true;
	}


	private void increaseGameState() {
		if (gameState == GameState.PRE_GAME) {
			gameState = GameState.MOVEMENT;
			gameMode.setup(this);
			allClearReady();
			
		} else if (gameState == GameState.MOVEMENT) {
			moveAllPlayers();
			moveAllNPCs();
			allResetActionStrings();
			allClearLastTurn();
			gameMode.setStartingLastTurnInfo();
			allClearReady();
			clearAllDeadNPCs();
			gameState = GameState.ACTIONS;
			
		} else if (gameState == GameState.ACTIONS) {
			executeAllActions();
			round = round + 1;
			
			gameMode.triggerEvents(this);
			if (gameMode.gameOver(this)) {
				gameState = GameState.PRE_GAME;
			} else {
				gameState = GameState.MOVEMENT;
			}
			allClearReady();
		}
		
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
					   ":" + cl.getSuit() +
					   ":" + cl.getItems() + 
					   ":" + cl.getRoomInfo() + 
					   ":" + cl.getLastTurnInfo();
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
		return gameMode.getAvailableJobs();
	}

	public Set<String> getAvailableJobsAsStrings() {
		return gameMode.getAvailCharsAsStrings();
	}

}
