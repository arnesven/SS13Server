package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.actions.Action;
import model.actions.AttackAction;
import model.actions.DropAction;
import model.actions.PickUpAction;
import model.actions.WatchAction;
import model.actions.DoNothingAction;
import model.actions.SearchAction;
import model.actions.Target;
import model.actions.TargetingAction;
import model.characters.GameCharacter;
import model.characters.InfectedCharacter;
import model.characters.InstanceChecker;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;
import model.npcs.NPC;


/**
 * @author erini02
 * Class for representing a client in the game. 
 * This means, a player, and the data pertaining to that player.
 */
public class Player extends Actor implements Target {
	
	private static final double MAX_HEALTH = 3.0;
	private boolean ready = false;
	private int nextMove = 0;
	
	private List<String> lastTurnInfo = new ArrayList<>();

	private String suit = "clothes";
	private Action nextAction;
	

	public Player() {
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
	@Override
	public void setCharacter(GameCharacter charr) {
		super.setCharacter(charr);
		getCharacter().setClient(this);
	}

	/**
	 * Gets the base name of the character of this client.
	 * The base name is the name of the character in its simplest
	 * form, e.g. "Doctor" or "Chaplain".
	 * @return
	 */
	public String getCharacterBaseName() {
		return getCharacter().getBaseName();
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
		return this.getCharacter().getItems();
	}
	
	/**
	 * Adds an item to this player.
	 * @param it the item to be added.
	 */
	public void addItem(GameItem it) {
		getCharacter().getItems().add(it);
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
		return getCharacter().getFullName();
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
		return result;
	}

	
	/**
	 * Parses a action object from the action string sent by the
	 * client gui.
	 * @param actionString the string describing which action was selected
	 * @param gameData the Game's data.
	 */
	public void parseActionFromString(String actionString, GameData gameData) {
		ArrayList<Action> at = getActionTree(gameData);
		String actionStr = actionString.replaceFirst("root,", "");

		ArrayList<String> strings = new ArrayList<>();
		strings.addAll(Arrays.asList(actionStr.split(",")));
		System.out.println("Action tree: " + at.toString());
		for (Action a : at) {
			if (a.getName().equals(strings.get(0))) {
				List<String> args = strings.subList(1, strings.size());
				a.setArguments(args);
				this.nextAction = a;
				return;
			}
		}	
		throw new NoSuchElementException("Could not find action for this action string " + actionString + ".");
	}

	/**
	 * Applies the action previously selected in the action string.
	 * This method finds the corresponding action in the action tree
	 * and executes it.
	 * @param gameData the Game's data.
	 */
	public void applyAction(GameData gameData) {
		if (!isDead()) {
			this.nextAction.printAndExecute(gameData, this);
		}
	}
	
	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
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



	@Override
	public String getName() {
		return getCharacterBaseName();
	}

	@Override
	public void beAttackedBy(Actor performingClient, Weapon weapon) {
		getCharacter().beAttackedBy(performingClient, weapon);
		if (isDead()) { // You died, to bad!
			while (!getItems().isEmpty()) {
				getPosition().addItem(getItems().remove(0));
			}
		}
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
			addRoomActions(at);
			addAttackActions(at);
			addWatchAction(at);
			addDropActions(at);
			addPickUpActions(at);
			addItemActions(at);
			getCharacter().addCharacterSpecificActions(gameData, at);
			
		}
		
		return at;
	}
	
	private void addItemActions(ArrayList<Action> at) {
		Map<String, GameItem> map = new HashMap<String, GameItem>();
		
		for (GameItem it : getItems()) {
			if (!map.containsKey(it.getName())) {
				it.addYourActions(at, this);
				map.put(it.getName(), it);
			}
		}
	}

	private void addPickUpActions(ArrayList<Action> at) {
		if (getCharacter().getPosition().getItems().size() > 0) {
			PickUpAction pickUpAction = new PickUpAction(this);
			at.add(pickUpAction);
		}
	}

	private void addDropActions(ArrayList<Action> at) {
		if (getItems().size() > 0) {
			DropAction dropAction = new DropAction(this);
			at.add(dropAction);
		}
	}

	private void addRoomActions(ArrayList<Action> at) {
		this.getPosition().addActionsFor(this, at);
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
			attackAction.addClientsItemsToAction(this);
			at.add(attackAction);
		}
	}
	
	private void addBasicActions(ArrayList<Action> at) {
		at.add(new DoNothingAction());
		if (!isDead()) {
			at.add(new SearchAction());
		}
	}

	public Action getNextAction() {
		return nextAction;
	}

	public double getMaxHealth() {
		return MAX_HEALTH;
	}

	@Override
	public double getHealth() {
		return this.getCurrentHealth();
	}

	@Override
	public String getPublicName() {
		return getCharacterPublicName();
	}



	@Override
	public Target getAsTarget() {
		return this;
	}

	@Override
	public double getSpeed() {
		return getCharacter().getSpeed();
	}

	@Override
	public void action(GameData gameData) {
		this.applyAction(gameData);
	}

	@Override
	public boolean isHuman() {
		return getCharacter().isHuman();
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		return getCharacter().hasSpecificReaction();
	}

	@Override
	public void addToHealth(double d) {
		getCharacter().setHealth(Math.min(getMaxHealth(), 
								 getCharacter().getHealth() + d));
	}
	
}
