package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.actions.Action;
import model.actions.AttackAction;
import model.actions.WatchAction;
import model.actions.DoNothingAction;
import model.actions.SearchAction;
import model.actions.Target;
import model.actions.TargetingAction;
import model.actions.Weapon;
import model.characters.GameCharacter;
import model.characters.InfectedCharacter;
import model.characters.InstanceChecker;
import model.map.Room;


/**
 * @author erini02
 * Class for representing a client in the game. 
 * This means, a player, and the data pertaining to that player.
 */
public class Client implements Target {
	
	private boolean ready = false;
	private int nextMove = 0;
	private GameCharacter character;
	private List<String> lastTurnInfo = new ArrayList<>();
	//TODO : shouldn't items belong to the character?
	private List<GameItem> items = new ArrayList<>();
	private String suit = "clothes";
	private String actionString;
	private Action lastAction = null;

	public Client() {
		lastTurnInfo.add("Game started - good luck!");
	}

	/**
	 * Sets the ready flag for the client. True meaning that the client is ready to continue
	 * to the next phase of the game. False means that the client is not ready yet.
	 * @param ready, the new value for the ready flag.
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	
	/**
	 * Gets wether or not the client is ready to continue or not
	 * @return true if the client is ready, false otherwise
	 */
	public boolean isReady() {
		return ready;
	}
	
	/**
	 * Returns wether or not the player is dead or not
	 * @return true if the client is dead
	 */
	public boolean isDead() {
		if (getCharacter() == null) {
			return false;
		}
		
		return getCharacter().getHealth() == 0.0;
	}

	/**
	 * Sets the character for this client.
	 * This will set the character for the client and also set the client reference
	 * in the character to this client.
	 * @param charr the new character
	 */
	public void setCharacter(GameCharacter charr) {
		this.character = charr;
		this.character.setClient(this);
	}

	/**
	 * Gets the base name of the character of this client.
	 * The base name is the name of the character in its simplest
	 * form, e.g. "Doctor" or "Chaplain".
	 * @return
	 */
	public String getCharacterBaseName() {
		return character.getBaseName();
	}


	/**
	 * Gets the current health of the player.
	 * This is a wrapper to the character's health.
	 * @return the health of the character of this player.
	 * @throws IllegalStateException if the character has not yet been set.
	 */
	public double getCurrentHealth() {
		if (getCharacter() == null) {
			throw new IllegalStateException("Character has not yet been set for this client.");
		}
		return getCharacter().getHealth();
	}
	
	/**
	 * Gets the Last turn info for this client.
	 * The last turn info is a collection of strings
	 * which tells the player what happened during the
	 * last turn of the game.
	 * @return The information as a list of strings.
	 */
	public List<String> getLastTurnInfo() {
		return this.lastTurnInfo ;
	}

	
	/**
	 * Gets the items of a player as a list
	 * @return the list of game items.
	 */
	public List<GameItem> getItems() {
		return this.items ;
	}
	
	/**
	 * Adds an item to this player.
	 * @param it the item to be added.
	 */
	public void addItem(GameItem it) {
		items.add(it);
	}

	/**
	 * Gets the suit of the player as a string.
	 * @return the suit of the player
	 */
	public String getSuit() {
		// TODO: This method will later return the name of game item representing that player's suit.
		return this.suit;
	}
	
	/**
	 * Sets the ID of the next move which the player will make.
	 * The GUI will send an ID of the room which the player has selected.
	 * @param id the ID representing the room which to the player will move next.
	 */
	public void setNextMove(Integer id) {
		nextMove = id;
	}

	/**
	 * Gets the ID of the room which this player will move to next.
	 * @return the ID of the room which this player will move to next.
	 */
	public int getNextMove() {
		return this.nextMove;
	}

	/**
	 * Gets the info for the player's current room. 
	 * The room info is a collection of strings which
	 * tells the player what other characters/items/objects
	 * are in that same room with him/her.
	 * @return the information as a list of strings.
	 */
	public List<String> getRoomInfo() {
		return this.getPosition().getInfo(this);
	}
	
	/**
	 * Gets the current position of this player's character.
	 * I.e. what room this player's character is currently in.
	 * @return the room of the player's character.
	 */
	public Room getPosition() {
		return getCharacter().getPosition();
	}

	/**
	 * Gets this player's character's real name.
	 * I.e. the full name, including all modifiers, like (host) or anthing else
	 * which has been added. This is the name as it would appear to the player itself.
	 * @return
	 */
	public String getCharacterRealName() {
		return character.getFullName();
	}
	
	/**
	 * Get's the client's characters public name, i.e. as it appears to
	 * anyone else on the station.
	 * @return the public name of the character
	 */
	public String getCharacterPublicName() {
		return getCharacter().getPublicName();
	}

	/**
	 * Returns the character of this player,
	 * or null if one has not yet been set.
	 * @return the player's character
	 */
	public GameCharacter getCharacter() {
		return character;
	}
	
	/**
	 * Gets all locations which can be selected by the player
	 * as potential destinations to which he/she can move to.
	 * Currently, all clients can move two rooms at a time.
	 * @param gameData the Game's data
	 * @return the locations IDs as an array.
	 */
	public int[] getSelectableLocations(GameData gameData) {
		
		int[] neigbors = this.getPosition().getNeighbors();
		ArrayList<Integer> movablePlaces = new ArrayList<>();
		
		for (int n : neigbors) {
			for (int n2 : gameData.getRoomForId(n).getNeighbors()) {
				if (! movablePlaces.contains(n2) ) {
					movablePlaces.add(n2);
				}
			}
			if (! movablePlaces.contains(n) ) {
				movablePlaces.add(n);
			}
		}
		movablePlaces.add(this.getPosition().getID());
		
		int[] result = new int[movablePlaces.size()];
		
		if (! this.isDead()) {
			for (int i = 0; i < movablePlaces.size(); ++i) {
				result[i] = movablePlaces.get(i);
			}
		}
		return result;
	}


	/**
	 * Moves the player into another room
	 * @param room the room to be moved into.
	 */
	public void moveIntoRoom(Room room) {
		if (!isDead()) {
			if (this.getPosition() != null) {
				this.getPosition().removePlayer(this);
			}
			System.out.println("Moving player inte room");
			this.setPosition(room);
			room.addPlayer(this);
		}
	}


	
	/**
	 * Gets the string representation of this players
	 * current action tree. I.e. the actions which this player can
	 * take depending on what room, what items and what other players
	 * are in that same room with him/her.
	 * @param gameData the Game's data
	 * @return the string representing the selectable actions.
	 */
	public String getActionTreeString(GameData gameData) {
		
		String result = "{";
		for (Action a : getActionTree(gameData)) {
			result += a.toString();
		}
		result += "}";
		System.out.println(result);
		return result;

	}

	/**
	 * Sets the action string of this player. I.e. the string
	 * representing what action this player will take next.
	 * @param rest the string representing the selected action.
	 */
	public void setActionString(String rest) {
		this.actionString = rest;
		
	}

	/**
	 * Adds a string to the last turn info.
	 * @param string the string to be added to the info.
	 */
	public void addTolastTurnInfo(String string) {
		lastTurnInfo.add(string);
	}
	
	/**
	 * Clears the last turn info of this client.
	 * The last turn info is usually cleared between turns.
	 */
	public void clearLastTurnInfo() {
		lastTurnInfo.clear();
	}


	/**
	 * Adds the player's character's public name to the room info.
	 * @param info the info to be added to.
	 */
	public void addYourselfToRoomInfo(ArrayList<String> info) {
		//TODO: This function should probably be deferred to the character.
		info.add(this.getCharacterPublicName());
	}

	/**
	 * Applies the action previously selected in the action string.
	 * This method finds the corresponding action in the action tree
	 * and executes it.
	 * @param gameData the Game's data.
	 */
	public void applyAction(GameData gameData) {
		if (!isDead()) {
			ArrayList<Action> at = getActionTree(gameData);
			String actionStr = actionString.replaceFirst("root,", "");

			ArrayList<String> strings = new ArrayList<>();
			strings.addAll(Arrays.asList(actionStr.split(",")));
			for (Action a : at) {
				if (a.getName().equals(strings.get(0))) {
					List<String> args = strings.subList(1, strings.size());
					a.setArguments(args);
					a.execute(gameData, this);
					this.lastAction = a;
				}
			}
		}
	}

	@Override
	public String getName() {
		return getCharacterBaseName();
	}

	@Override
	public void beAttackedBy(Client performingClient, Weapon weapon) {
		getCharacter().beAttackedBy(performingClient, weapon);		
	}


	/**
	 * Returns wether or not this client has been infected.
	 * This starts to check the instance of the character and
	 * its inner characters.
	 * @return true if this client's character is infected. False otherwise.
	 * @throws IllegalStateException if the client's character has not yet been set.
	 */
	public boolean isInfected() {
		if (getCharacter() == null) {
			throw new IllegalStateException("This client's character has not yet been set.");
		}
		
		InstanceChecker infectChecker = new InstanceChecker() {
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof InfectedCharacter;
			}
		};
		
		return getCharacter().checkInstance(infectChecker);
	}

	@Override
	public boolean isTargetable() {
		return !isDead();
	}
	
	
	/**
	 * Sets the current position (room) of this player's character
	 * @param room the new position
	 */
	private void setPosition(Room room) {
		getCharacter().setPosition(room);
	}
	
	/**
	 * Gets the tree structure of selectable actions from which the player
	 * can select one. Some actions require subinformation, which is why this
	 * datastructure is a tree.
	 * @param gameData the Game's data
	 * @return the tree of actions.
	 */
	private ArrayList<Action> getActionTree(GameData gameData) {
		ArrayList<Action> at = new ArrayList<Action>();
		addBasicActions(at);
		if (!isDead()) {
			addAttackActions(at);
			addWatchAction(at);
			
			getCharacter().addCharacterSpecificActions(gameData, at);

		}
		
		return at;
	}
	
	private void addWatchAction(ArrayList<Action> at) {
		TargetingAction watchAction = new WatchAction(this);
		if (watchAction.getNoOfTargets() > 0) {
			at.add(watchAction);
		}
	}

	private void addAttackActions(ArrayList<Action> at) {
		TargetingAction attackAction = new AttackAction(this);
		if (attackAction.getNoOfTargets() > 0) {
			attackAction.addItemsToAction(this);
			at.add(attackAction);
		}
	}
	
	private void addBasicActions(ArrayList<Action> at) {
		at.add(new DoNothingAction("Do Nothing"));
		if (!isDead()) {
			at.add(new SearchAction("Search Room"));
		}
	}

}
