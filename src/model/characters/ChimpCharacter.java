package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SpeechAction;
import model.items.GameItem;
import model.items.foods.Banana;
import model.items.weapons.LaserPistol;
import model.items.weapons.Weapon;
import model.map.Room;

public class ChimpCharacter extends AnimalCharacter {

	private Room startRoom;

	public ChimpCharacter(Room r) {
		super("Chimp", r.getID(), -2.0);
		this.startRoom = r;
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
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		at.add(new SpeechAction("Oooh oooh  ah!"));
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

	@Override
	public GameCharacter clone() {
		return new ChimpCharacter(this.startRoom);
	}
	
	@Override
	public boolean hasInventory() {
		return true;
	}
	
	
}
