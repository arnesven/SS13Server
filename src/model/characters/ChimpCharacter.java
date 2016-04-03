package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.GameItem;
import model.items.foods.Banana;
import model.items.weapons.LaserPistol;
import model.items.weapons.Weapon;
import model.map.Room;

public class ChimpCharacter extends AnimalCharacter {

	public ChimpCharacter(Room r) {
		super("Chimp", r.getID(), -2.0);
		this.removeSuit();
	}

	@Override
	public List<GameItem> getStartingItems() {
		
		ArrayList<GameItem> arr = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			arr.add(new Banana());
		}
		
		return arr;
		
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return ']';
	}

	private static Weapon feces = new Weapon("Feces", 1.0, 0.0, false, 0.0);
	
	@Override
	public Weapon getDefaultWeapon() {
		return feces;
	}
}
