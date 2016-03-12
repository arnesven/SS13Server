package model.items;

import java.util.ArrayList;

import model.Actor;
import model.Player;
import model.actions.Action;

/**
 * @author erini02
 * Class representing an Item in the game.
 * An item is an object which can be held, dropped, picked up or used
 * by a character.
 */
public class GameItem {

	private String name;
	
	public GameItem(String string) {
		name = string;
	}

	/**
	 * Gets the name of this item
	 * @return the item's name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add("i" + name);
	}

	/**
	 * Adds the items actions to the players list of actions.
	 * Overload this method for specific items so they add their
	 * actions.
	 * @param at
	 */
	public void addYourActions(ArrayList<Action> at, Player cl) {
	}
	
}
