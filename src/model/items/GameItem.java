package model.items;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.characters.GameCharacter;
import model.map.Room;

/**
 * @author erini02
 * Class representing an Item in the game.
 * An item is an object which can be held, dropped, picked up or used
 * by a character.
 */
public abstract class GameItem implements Locatable {

	private String name;
	private double weight;
	private Room position;
	private GameCharacter holder = null;
	
	public GameItem(String string, double weight) {
		this.name = string;
		this.weight = weight;
	}
	
	public abstract GameItem clone();

	/**
	 * Gets the name of this item as it appears to a person
	 * who knows everything about it (for instance someone owning it).
	 * @param whosAsking, can be null, check before using!
	 * @return
	 */
	public String getFullName(Actor whosAsking) {
		return name;
	}
	

	/**
	 * Gets the name of this item as it appears from a distance.
	 * @param whosAsking, can be null! check before using!
	 * @return
	 */
	public String getPublicName(Actor whosAsking) {
		return name;
	}
	
	/**
	 * Gets the true name of this item, as it was instanciated.
	 * @return
	 */
	public String getBaseName() {
		return name;
	}
	
	
	
	@Override
	public final String toString() {
		return super.toString();
	}

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add("i" + getPublicName(whosAsking));
	}

	/**
	 * Adds the items actions to the players list of actions.
	 * Overload this method for specific items so they add their
	 * actions.
	 * @param at
	 */
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
	}

	public double getWeight() {
		return weight;
	}

	protected void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public Room getPosition() {
		if (holder != null) {
			return holder.getPosition();
		}
		return position;
	}
	
	public void setPosition(Room p) {
		position = p;
	}

	public void setHolder(GameCharacter gameCharacter) {
		this.holder = gameCharacter;
		
	}
	
	public static <E> boolean hasAnItem(Actor act, E obj) {
		for (GameItem it : act.getItems()) {
			if (it.getClass() == obj.getClass()) {
				return true;
			}
		}
		return false;
	}

	public static <E> boolean containsItem(List<GameItem> startingItems, E obj) {
		for (GameItem it : startingItems) {
			if (it.getClass() == obj.getClass()) {
				return true;
			}
		}
		return false;
	}

	public static Locatable getItem(Player victim, GameItem item) {
		for (GameItem it : victim.getItems()) {
			if (it.getClass() == item.getClass()) {
				return it;
			}
		}
		throw new NoSuchElementException("Did not find a " + item.getBaseName());
	}
	

}
