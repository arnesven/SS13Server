package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Flamer;
import model.items.weapons.Knife;

public class BiologistCharacter extends GameCharacter {

	public BiologistCharacter() {
		super("Biologist", 3, 11.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Flamer());
		return list;
	}

}
