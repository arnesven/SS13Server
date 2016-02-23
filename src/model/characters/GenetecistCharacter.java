package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;

public class GenetecistCharacter extends GameCharacter {

	public GenetecistCharacter() {
		super("Genetecist", 1, 8.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Chemicals());
		return list;
	}

}
