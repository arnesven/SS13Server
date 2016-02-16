package model.characters;

import java.util.ArrayList;

import model.Client;
import model.GameData;
import model.actions.Action;
import model.actions.Weapon;

public class GameCharacter {
	
	private String name;
	private int startingRoom = 0;
	private double health = 3.0;
	private Client client = null;

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
	public String getName() {
		return name + (isDead()?" (dead)":"");
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
	public String getRealName() {
		return getName();
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
											   getName() + " with " + weapon.getName() + ".");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getCharacterName() + " " + 
											 weapon.getSuccessfulMessage() + "ed you with " + 
											 weapon.getName() + "."); 
			}
			
		} else {
			performingClient.addTolastTurnInfo("Your attacked missed!");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getCharacterName() + " tried to " + 
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

}
