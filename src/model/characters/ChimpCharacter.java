package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.weapons.LaserPistol;
import model.map.Room;

public class ChimpCharacter extends GameCharacter {

	public ChimpCharacter(Room r) {
		super("I want to Kill Erik", r.getID(), 24.0);
		
	}

	@Override
	public List<GameItem> getStartingItems() {
		
		ArrayList<GameItem> arr = new ArrayList<>();
		arr.add(new LaserPistol());
		return arr;
		
	}
	
	

}
