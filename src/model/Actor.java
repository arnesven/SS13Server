package model;

import java.util.ArrayList;
import java.util.List;

import model.actions.Target;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.map.Room;
import model.npcs.NPC;

public abstract class Actor  {

	private GameCharacter character = null;
	
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
	
	public abstract Room getPosition();

	public abstract void addTolastTurnInfo(String string);
	
	public abstract boolean isInfected();

	public abstract String getPublicName();

	public abstract String getBaseName();
	
	public abstract Target getAsTarget();

	public abstract List<GameItem> getItems();

	public abstract double getSpeed();

	public abstract void action(GameData gameData);

	public abstract void addYourselfToRoomInfo(ArrayList<String> info);

}
