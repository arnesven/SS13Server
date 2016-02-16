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
import model.map.Room;

public class Client implements Target {
	
	private boolean ready = false;
	private Room position = null;
	private int nextMove = 0;
	private GameCharacter character;
	private List<String> lastTurnInfo = new ArrayList<>();
	private List<GameItem> items = new ArrayList<>();
	private String suit = "clothes";
	private String actionString;
	private Action lastAction = null;

	public Client() {
		lastTurnInfo.add("Game started - good luck!");
	}
	
	public Boolean getReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isReady() {
		return ready;
	}

	public void setCharacter(GameCharacter charr, Client c) {
		this.character = charr;
		this.character.setClient(c);
	}

	public String getCharacterName() {
		return character.getName();
	}

	public int getCurrentPositionID() {
		return position.getID();
	}

	public double getCurrentHealth() {
		return getCharacter().getHealth();
	}

	public int[] getSelectableLocations(GameData gameData) {
		
		int[] neigbors = this.position.getNeighbors();
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
		movablePlaces.add(this.position.getID());
		
		int[] result = new int[movablePlaces.size()];
		
		if (! this.isDead()) {
			for (int i = 0; i < movablePlaces.size(); ++i) {
				result[i] = movablePlaces.get(i);
			}
		}
		return result;
	}

	public List<String> getLastTurnInfo() {
		return this.lastTurnInfo ;
	}

	public List<GameItem> getItems() {
		return this.items ;
	}

	public String getSuit() {
		return this.suit;
	}

	public List<String> getRoomInfo() {
		return this.position.getInfo(this);
	}


	public void moveIntoRoom(Room room) {
		if (!isDead()) {
			if (this.position != null) {
				this.position.removePlayer(this);
			}
			System.out.println("Moving player inte room");
			this.position = room;
			room.addPlayer(this);
		}
	}
	
	public void setNextMove(Integer id) {
		nextMove = id;
	}

	public int getNextMove() {
		return this.nextMove;
	}

	public ArrayList<Action> getActionTree(GameData gameData) {
		ArrayList<Action> at = new ArrayList<Action>();
		addBasicActions(at);
		if (!isDead()) {
			if (getPosition().getClients().size() > 1) {
				addAttackActions(at);
				addAvoidActions(at);
			}
			getCharacter().addCharacterSpecificActions(gameData, at);

		}
		
		return at;
	}
	
	public String getActionTreeString(GameData gameData) {
		
		String result = "{";
		for (Action a : getActionTree(gameData)) {
			result += a.toString();
		}
		result += "}";
		System.out.println(result);
		return result;

	}

	private void addAvoidActions(ArrayList<Action> at) {
		TargetingAction avoidAction = new WatchAction(this);
//		avoidAction.addTargetsToAction(this);
		at.add(avoidAction);
	}

	private void addAttackActions(ArrayList<Action> at) {
		TargetingAction attackAction = new AttackAction(this);
//		attackAction.addTargetsToAction(this);
		attackAction.addItemsToAction(this);
		at.add(attackAction);
	}
	
	

	private void addBasicActions(ArrayList<Action> at) {
		at.add(new DoNothingAction("Do Nothing"));
		if (!isDead()) {
			at.add(new SearchAction("Search Room"));
		}
	}

	public void setActionString(String rest) {
		this.actionString = rest;
		
	}

	public Room getPosition() {
		return position;
	}

	public int getStartingRoom() {
		return character.getStartingRoom();
	}

	public String getCharacterRealName() {
		return character.getRealName();
	}

	public GameCharacter getCharacter() {
		return character;
	}

	public void addTolastTurnInfo(String string) {
		lastTurnInfo.add(string);
	}


	public void addYourselfToRoomInfo(ArrayList<String> info) {
		info.add(this.getCharacterName());
	}

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
		return getCharacterName();
	}

	@Override
	public void beAttackedBy(Client performingClient, Weapon weapon) {
		getCharacter().beAttackedBy(performingClient, weapon);		
	}

	public void clearLastTurnInfo() {
		lastTurnInfo.clear();
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

	public void addItem(GameItem it) {
		items.add(it);
	}


}
