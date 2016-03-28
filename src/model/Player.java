package model;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.actions.Action;
import model.actions.AttackAction;
import model.actions.DropAction;
import model.actions.GiveAction;
import model.actions.PickUpAction;
import model.actions.PutOnAction;
import model.actions.WatchAction;
import model.actions.DoNothingAction;
import model.actions.SearchAction;
import model.actions.TargetingAction;
import model.characters.GameCharacter;
import model.characters.decorators.InfectedCharacter;
import model.characters.decorators.InstanceChecker;
import model.events.Damager;
import model.items.Explosive;
import model.items.GameItem;
import model.items.Grenade;
import model.items.MedKit;
import model.items.weapons.Weapon;
import model.map.Room;
import model.npcs.NPC;


/**
 * @author erini02
 * Class for representing a client in the game. 
 * This means, a player, and the data pertaining to that player.
 */
public class Player extends Actor implements Target {

	public static final double MAX_HEALTH = 3.0;
	private boolean ready = false;
	private int nextMove = 0;
	//private List<String> lastTurnInfo = new ArrayList<>();
	private List<String> personalHistory = new ArrayList<>();
	private Action nextAction;
	private HashMap<String, Boolean> jobChoices = new HashMap<>();


	public Player(GameData gameData) {
//		for (String s : gameData.getAvailableJobsAsStrings() ) {
//			jobChoices.put(s, true);
//		}
		//jobChoices.put("Host", true);
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
		List<String> lastTurnInfo = new ArrayList<>();
		lastTurnInfo.addAll(personalHistory);

		return lastTurnInfo ;
	}

	/**
	 * Gets the suit of the player as a string.
	 * @return the suit of the player
	 */
	public String getSuit() {
		if (this.getCharacter().getSuit() == null) {
			return "*None*";
		}
		return this.getCharacter().getSuit().getFullName(this);
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
	 * @return the locations IDs as an array
	 */
	public int[] getSelectableLocations(GameData gameData) {
		int steps = getCharacter().getMovementSteps();
		if (getCharacter().isEncumbered()) {
			addTolastTurnInfo("You are carrying to much to be able to run!");
		}
		
		ArrayList<Integer> movablePlaces = new ArrayList<>();

		if (steps > 0) {
			int[] neigbors = this.getPosition().getNeighbors();

			for (int n : neigbors) {
				addNeighborsRecursively(gameData, n, movablePlaces, steps-1);
			}
		}
		movablePlaces.add(this.getPosition().getID());

		int[] result = new int[movablePlaces.size()];
		for (int i = 0; i < movablePlaces.size(); ++i) {
			result[i] = movablePlaces.get(i);
		}
		return result;
	}


	private void addNeighborsRecursively(GameData gameData, int n,
			ArrayList<Integer> movablePlaces, int steps) {
		if (! movablePlaces.contains(n) ) {
			movablePlaces.add(n);
		}
		if (steps > 0) {
			for (int n2 : gameData.getRoomForId(n).getNeighbors()) {
				addNeighborsRecursively(gameData, n2, movablePlaces, steps-1);
			}
		}
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
			System.out.println("Moving player " + this.getCharacterRealName() + 
					" into room " + room.getName());
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
	public String getActionListString(GameData gameData) {		
		String result = "{";
		for (Action a : getActionList(gameData)) {
			result += a.getOptions(gameData, this).makeBracketedString();
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
		ArrayList<Action> at = getActionList(gameData);
		String actionStr = actionString.replaceFirst("root,", "");

		ArrayList<String> strings = new ArrayList<>();
		strings.addAll(Arrays.asList(actionStr.split(",")));
		//System.out.println("Action tree: " + at.toString());
		for (Action a : at) {
			if (a.getName().equals(strings.get(0))) {
				List<String> args = strings.subList(1, strings.size());
				a.setArguments(args, this);
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
			this.nextAction.doTheAction(gameData, this);
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
		personalHistory.add(string);
	}

	/**
	 * Clears the last turn info of this client.
	 * The last turn info is usually cleared between turns.
	 */
	public void clearLastTurnInfo() {
		personalHistory.clear();
	}

	@Override
	public String getName() {
		return getPublicName();
	}

	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
		return getCharacter().beAttackedBy(performingClient, weapon);

	}

	@Override
	public boolean isTargetable() {
		return !isDead();
	}

	/**
	 * Gets the tree structure of selectable actions from which the player
	 * can select one. Some actions require subinformation, which is why this
	 * datastructure is a tree.
	 * @param gameData the Game's data
	 * @return the tree of actions.
	 */
	private ArrayList<Action> getActionList(GameData gameData) {
		ArrayList<Action> at = new ArrayList<Action>();
		addBasicActions(at);
		if (!isDead()) {
			addRoomActions(gameData, at);
			addItemActions(gameData, at);
			addAttackActions(at);
			addWatchAction(at);
			addGiveAction(at);
			addDropActions(at);
			addPickUpActions(at);
			addPutOnActions(at);
			getCharacter().addCharacterSpecificActions(gameData, at);

		}

		return at;
	}



	

	private void addItemActions(GameData gameData, ArrayList<Action> at) {
		Map<String, GameItem> map = new HashMap<String, GameItem>();

		for (GameItem it : getItems()) {
			if (!map.containsKey(it.getBaseName())) {
				it.addYourActions(gameData, at, this);
				map.put(it.getFullName(this), it);
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



	private void addRoomActions(GameData gameData, ArrayList<Action> at) {
		this.getPosition().addActionsFor(gameData, this, at);
	}

	private void addWatchAction(ArrayList<Action> at) {
		TargetingAction watchAction = new WatchAction(this);
		if (watchAction.getNoOfTargets() > 0) {
			at.add(watchAction);
		}
	}

	private void addGiveAction(ArrayList<Action> at) {
		TargetingAction giveAction = new GiveAction(this);
		giveAction.addClientsItemsToAction(this);
		if (giveAction.getNoOfTargets() > 0 && giveAction.getWithWhats().size() > 0) {
			at.add(giveAction);
		}
	}

	private void addPutOnActions(ArrayList<Action> at) {
		PutOnAction act = new PutOnAction(this);
		if (act.getNoOfOptions() > 0) {
			at.add(act);
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
	public Target getAsTarget() {
		return this;
	}

	@Override
	public void action(GameData gameData) {
		this.applyAction(gameData);
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		return getCharacter().hasSpecificReaction(objectRef);
	}

	@Override
	public void addToHealth(double d) {
		getCharacter().setHealth(Math.min(getMaxHealth(), 
				getCharacter().getHealth() + d));
	}

	public void parseJobChoices(String rest) {
		String withoutBraces = rest.substring(2, rest.length()-1);
		System.out.println(withoutBraces);
		String[] opts = withoutBraces.split(",");
		for (String str : opts) {
			String[] keyVal = str.split("=");
			jobChoices.put(keyVal[0], Boolean.parseBoolean(keyVal[1]));
		}
		System.out.println("JobChoices " + jobChoices.toString());

	}

	public boolean checkedJob(String string) {
		if (jobChoices.get(string) == null) {
			return true;
		}
		return jobChoices.get(string);
	}

	public void beInfected(Actor performingClient) {
		this.setCharacter(new InfectedCharacter(this.getCharacter(), performingClient));
		this.addTolastTurnInfo("You were infected by " + performingClient.getPublicName() + 
				"! You are now on the Host team. Keep the humans from destroying the hive!");

	}

	public void prepForNewGame() {
		this.nextMove = 0;
		this.nextAction = null;
		this.personalHistory = new ArrayList<>();

	}

	@Override
	public void beExposedTo(Actor performingClient, Damager damager) {
		getCharacter().beExposedTo(performingClient, damager);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public boolean isHealable() {
		return getCharacter().isHealable();
	}

	public List<String> getItemsAsFullNameList() {
		List<String> strs = new ArrayList<>();
		for (GameItem gi : getItems()) {
			strs.add(gi.getFullName(this));
		}
		return strs;
	}

	public void addUniquelyTolastTurnInfo(String text) {
		for (String s : this.getLastTurnInfo()) {
			if (s.equals(text)) {
				return;
			}
		}
		this.addTolastTurnInfo(text);
	}


}
