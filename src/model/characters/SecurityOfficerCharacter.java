package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.LaserPistol;

public class SecurityOfficerCharacter extends GameCharacter {

	public SecurityOfficerCharacter() {
		super("Security Officer", 18, 14.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new LaserPistol());
		return list;
	}

}
