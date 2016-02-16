package model.characters;

import java.util.ArrayList;

import model.Client;
import model.GameData;
import model.actions.Action;
import model.actions.Weapon;
import model.map.Room;

public class GameCharacter {
	
	private String name;
	private int startingRoom = 0;
	private double health = 3.0;
	private Client client = null;
	private Room position = null;

	public GameCharacter(String name) {
		this.name = name;
	}
	
	public GameCharacter(String name, int startRoom) {
		this(name);
		this.startingRoom = startRoom;
	}

	/**
	 * @return the name of the character as it appears publicly
	 */
	public String getPublicName() {
		return name + (isDead()?" (dead)":"");
	}
	
	/**
	 * Gets the name of this character, for instance "Captain" or "Doctor"
	 * @return
	 */
	public String getBaseName() {
		return name;
	}

	/**
	 * @return the room in which the character starts
	 */
	public int getStartingRoom() {
		return startingRoom;
	}

	/**
	 * @return the name of the character as it appears for anyone knowing the TRUTH.
	 */
	public String getFullName() {
		return getBaseName();
	}

	private boolean isDead() {
		return health == 0.0;
	}

	/**
	 * @return true if this character can be targeted by attacks and interacted with through actions
	 */
	public boolean isInteractable() {
		return !isDead();
	}

	public void beAttackedBy(Client performingClient, Weapon weapon) {
		Client thisClient  = this.getClient();
		if (weapon.isAttackSuccessful()) {
			health = Math.max(0.0, health - weapon.getDamage());
			performingClient.addTolastTurnInfo("You " + weapon.getSuccessfulMessage() + "ed " + 
											   getBaseName() + " with " + weapon.getName() + ".");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getCharacterPublicName() + " " + 
											 weapon.getSuccessfulMessage() + "ed you with " + 
											 weapon.getName() + "."); 
			}
			
		} else {
			performingClient.addTolastTurnInfo("Your attacked missed!");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getCharacterPublicName() + " tried to " + 
											 weapon.getSuccessfulMessage() + " you with " + 
											 weapon.getName() + "."); 
			}
		}
		
	}

	public Client getClient() {
		return client;
	}
	
	public void setClient(Client c) {
		this.client = c;
	}

	/**
	 * Gets the health of this character.
	 * @return the health of this character.
	 */
	public double getHealth() {
		return health;
	}

	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) { 
		// No specific actions for ordinary characters...
	}

	public Room getPosition() {
		return position ;
	}

	public void setPosition(Room room) {
		position = room;
	}

	public boolean checkInstance(InstanceChecker infectChecker) {
		return infectChecker.checkInstanceOf(this);
	}


}
