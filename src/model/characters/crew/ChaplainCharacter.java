package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Bible;
import model.items.GameItem;
import model.items.MedKit;

public class ChaplainCharacter extends GameCharacter {

	public ChaplainCharacter() {
		super("Chaplain", 2, 2.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Bible());
		return list;
	}

}
