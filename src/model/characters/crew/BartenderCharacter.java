package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.weapons.Shotgun;

public class BartenderCharacter extends GameCharacter {

	public BartenderCharacter() {
		super("Bartender", 10, 4.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Shotgun());
		return list;
	}

}
