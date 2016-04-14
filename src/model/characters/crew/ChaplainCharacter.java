package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.general.Bible;
import model.items.general.GameItem;
import model.items.general.MedKit;

public class ChaplainCharacter extends CrewCharacter {

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

	@Override
	public GameCharacter clone() {
		return new ChaplainCharacter();
	}

}
