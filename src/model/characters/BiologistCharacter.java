package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Flamer;
import model.items.GameItem;
import model.items.Knife;
import model.items.MedKit;

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
