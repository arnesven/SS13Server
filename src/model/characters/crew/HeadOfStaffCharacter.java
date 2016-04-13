package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.suits.Sweater;
import model.items.weapons.LaserPistol;

public class HeadOfStaffCharacter extends CrewCharacter {

	public HeadOfStaffCharacter() {
		super("Head of Staff", 14, 15.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new KeyCard());
		list.add(new Sweater());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new HeadOfStaffCharacter();
	}

}
