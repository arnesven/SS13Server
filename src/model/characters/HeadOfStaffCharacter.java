package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.KeyCard;
import model.items.LaserPistol;
import model.items.MedKit;

public class HeadOfStaffCharacter extends GameCharacter {

	public HeadOfStaffCharacter() {
		super("Head of Staff", 14, 15.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new KeyCard());
		return list;
	}

}
