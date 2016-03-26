package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.suits.SunGlasses;
import model.items.weapons.Revolver;


public class DetectiveCharacter extends GameCharacter {

	public DetectiveCharacter() {
		super("Detective", 12, 13.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Revolver());
		list.add(new SunGlasses());
		return list;
	}

}
