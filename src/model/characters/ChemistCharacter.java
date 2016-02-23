package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Chemicals;
import model.items.GameItem;
import model.items.Knife;
import model.items.MedKit;

public class ChemistCharacter extends GameCharacter {

	public ChemistCharacter() {
		super("Chemist", 1, 9.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new Chemicals());
		return list;
	}

}
