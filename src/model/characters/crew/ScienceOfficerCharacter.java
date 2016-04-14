package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.GeigerMeter;

public class ScienceOfficerCharacter extends CrewCharacter {

	public ScienceOfficerCharacter() {
		super("Science Officer", 1, 9.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new GeigerMeter());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new ScienceOfficerCharacter();
	}

}
