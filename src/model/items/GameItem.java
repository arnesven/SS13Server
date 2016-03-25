package model.items;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;

/**
 * @author erini02
 * Class representing an Item in the game.
 * An item is an object which can be held, dropped, picked up or used
 * by a character.
 */
public abstract class GameItem {

	private String name;
	private double weight;
	
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

}
