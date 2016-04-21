package model;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InfectedCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.NoSuchInstanceException;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.map.Room;

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
		getCharacter().setActor(this);
		if (charr instanceof CharacterDecorator) {
			charr.printInstances();
		}
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
	 * @return the base name of the Actor.
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
	 * @param giver 
	 */
	public void addItem(GameItem it, Target giver) {
		getCharacter().giveItem(it, giver);
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
	 * @param whosAsking 
	 */
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add(getCharacter().getIcon(whosAsking) + whosAsking.getCharacter().getHowPerceived(this));
	}
	
	
	public boolean isDead() {
		return getCharacter().isDead();
	}

	public void removeInstance(InstanceChecker check) {
		if (getCharacter() instanceof CharacterDecorator) {
			CharacterDecorator decor = (CharacterDecorator)getCharacter();
			if (check.checkInstanceOf(decor)) {
				this.setCharacter(decor.getInner());
			} else {
				decor.removeInstance(check);
			}
			
		} else {
		    throw new NoSuchInstanceException("Could not remove instance!");
		}
	}
	
	public void putOnSuit(SuitItem selectedItem) {
		this.getCharacter().putOnSuit(selectedItem);
		selectedItem.beingPutOn(this);	
	}
	
	public void takeOffSuit() {
		SuitItem item = getCharacter().getSuit();
		this.getCharacter().removeSuit();
		item.beingTakenOff(this);
	}

    public void addToHealth(double d) {
        getCharacter().setHealth(Math.min(getMaxHealth(),
                getCharacter().getHealth() + d));
    }

    public void subtractFromHealth(double d) {
        getCharacter().setHealth(Math.max(0.0,
                getCharacter().getHealth() - d));
    }

	public abstract double getMaxHealth();
	
	public abstract void moveIntoRoom(Room brig);
}
