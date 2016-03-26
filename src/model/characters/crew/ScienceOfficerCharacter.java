package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.GameItem;
import model.items.GeigerMeter;
import model.items.MedKit;
import model.items.weapons.Knife;

public class ScienceOfficerCharacter extends GameCharacter {

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

}
