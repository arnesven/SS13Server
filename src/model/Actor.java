package model;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.characters.decorators.InfectedCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.InstanceRemover;
import model.items.GameItem;
import model.map.Room;
import model.npcs.NPC;

public abstract class Actor  {

	private GameCharacter character = null;
	
	public abstract void addTolastTurnInfo(String string);
	public abstract Target getAsTarget();
	public abstract void action(GameData gameData);
	public abstract void beInfected(Actor performingClient);
	
	
	/**
	 * Returns the character of this Actor,
	 * or null if one has not yet been set.
	 * @return the Actor's character
	 */
	public GameCharacter getCharacter() {
		return character;
	}
	
	/**
	 * Sets the character for this Actor.
	 * This will set the character for the Actor.
	 * @param charr the new character
	 */
	public void setCharacter(GameCharacter charr) {
		this.character = charr;
	}
	
	/**
	 * Gets the current position of this Actor's character.
	 * I.e. what room this actor's character is currently in.
	 * @return the room of the actor's character.
	 */
	public Room getPosition() {
		return getCharacter().getPosition();
	}

	/**
	 * Sets the current position (room) of this actor's character
	 * @param room the new position
	 */
	public void setPosition(Room room) {
		getCharacter().setPosition(room);
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
			throw new IllegalStateException("This actor's character has not yet been set.");
		}
		
		InstanceChecker infectChecker = new InstanceChecker() {
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof InfectedCharacter;
			}
		};
		
		return getCharacter().checkInstance(infectChecker);
	}

	public String getPublicName() {
		return getCharacter().getPublicName();
	}

	/**
	 * Gets the base name of the character of this actor.
	 * The base name is the name of the character in its simplest
	 * form, e.g. "Doctor" or "Chaplain".
	 * @return
	 */
	public String getBaseName() {
		return getCharacter().getBaseName();
	}
	
	/**
	 * Gets the items of an actor as a list
	 * @return the list of game items.
	 */
	public List<GameItem> getItems() {
		return getCharacter().getItems();
	}

	/**
	 * Adds an item to this player.
	 * @param it the item to be added.
	 */
	public void addItem(GameItem it) {
		getCharacter().getItems().add(it);
	}

	/**
	 * Gets the speed of this actor's character.
	 * The speed determines in what order actions are executed.
	 * @return the speed of the actor's character.
	 */
	public double getSpeed() {
		return getCharacter().getSpeed();
	}
	

	/**
	 * Adds the player's character's public name to the room info.
	 * @param info the info to be added to.
	 */
	public void addYourselfToRoomInfo(ArrayList<String> info) {
		//TODO: This function should probably be deferred to the character.
		info.add("a" + this.getPublicName());
	}
	
	
	
	public boolean isDead() {
		return getCharacter().isDead();
	}

	public void removeInstance(InstanceRemover fireProtectionRemover) {
		System.out.println("Removing an instance..");
		this.setCharacter(fireProtectionRemover.removeInstance(getCharacter()));
	}

}