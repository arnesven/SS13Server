package model;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import model.actions.DoNothingAction;
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
	private HashMap<String, Client> clients = new HashMap<>();
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
		for (Entry<String, Client> e : clients.entrySet()) {
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
	public Client getClient(String clid) {
		return clients.get(clid);
	}


	/**
	 * Creates a new client and adds it to the game.
	 * The client gets a unique ID called a CLID.
	 * @return the CLID of the new client.
	 */
	public String createNewClient() {
		Random rand = new Random();
		String clid = null;
		do {
			clid = new String("CL" + (rand.nextInt(900)+100));
		} while (getClientsAsMap().containsKey(clid));
		clients.put(clid, new Client());
		
		return clid;
	}


	/**
	 * Removes a client from the game.
	 * @param otherPlayer, the client to be removed.
	 */
	public void removeClient(String otherPlayer) {
		clients.remove(otherPlayer);
	}


	/**
	 * Finds a certain client and sets its ready status.
	 * @param clid, the client whose status to set.
	 * @param equals, the new ready status for the client.
	 */
	public void setCientReady(String clid, boolean equals) {
		clients.get(clid).setReady(equals);
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
		Client cl = clients.get(clid);
		String result =  Arrays.toString(cl.getSelectableLocations(this)) + ":" + createBasicPlayerData(cl);
		return result;
	}


	/**
	 * Create a string with the player's data and available actions.
	 * @param clid the player to create the data for.
	 * @return a string representation of the player data.
	 */
	public String createPlayerActionData(String clid) {
		Client cl = clients.get(clid);
		String result = cl.getActionTreeString(this) + ":" + createBasicPlayerData(cl);
		return result;
	}

	
	/**
	 * Gets the clients as a collection.
	 * @return the collection of clients.
	 */
	public Collection<Client> getClients() {
		return clients.values();
	}

	
	private void allClearReady() {
		for (Client c : clients.values()) {
			c.setReady(false);
		}
	}


	private boolean allClientsReadyOrDead() {
		for (Client c : clients.values()) {
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
			round = round + 1;
			moveAllPlayers();
			moveAllNPCs();
			allResetActionStrings();
			allClearLastTurn();
			gameMode.setStartingLastTurnInfo();
			allClearReady();
			gameState = GameState.ACTIONS;
			
		} else if (gameState == GameState.ACTIONS) {
			actionsForAllPlayers();
			actionsForAllNPCs();
			if (allDead() || gameMode.gameOver(this)) {
				gameState = GameState.PRE_GAME;
			} else {
				gameState = GameState.MOVEMENT;
			}
			allClearReady();
		}
		
	}





	private boolean allDead() {
		for (Client cl : clients.values()) {
			if (!cl.isDead()) {
				return false;
			}
		}
		
		return true;
	}

	private void allClearLastTurn() {
		for (Client cl : clients.values()) {
			cl.clearLastTurnInfo();
		}
	}

	private void allResetActionStrings() {
		for (Client cl : clients.values()) {
			cl.setNextAction(new DoNothingAction());
		}
	}

	private void actionsForAllPlayers() {
		for (Client cl : clients.values()) {
			cl.applyAction(this);
		}
		
	}

	private void moveAllPlayers() {
		for (Client cl : clients.values()) {
			cl.moveIntoRoom(map.getRoomForID(cl.getNextMove()));
		}
	}
	
	private void moveAllNPCs() {
		for (NPC npc : npcs) {
			System.out.println("Moving NPC " + npc.getName());
			npc.moveAccordingToBehavior();
		}
	}
	

	private void actionsForAllNPCs() {
		for (NPC npc : npcs) {
			npc.actAccordingToBehavior(this);
		}
		
	}

	private String createBasicPlayerData(Client cl) {		
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

}
