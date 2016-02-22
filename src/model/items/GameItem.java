package model.items;

import java.util.ArrayList;

import model.Client;

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
		return name;
	}

	public void addYourselfToRoomInfo(ArrayList<String> info, Client whosAsking) {
		info.add(name);
	}

}
