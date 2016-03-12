package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.weapons.LaserPistol;
import model.items.weapons.StunBaton;

public class SecurityOfficerCharacter extends GameCharacter {

	public SecurityOfficerCharacter() {
		super("Security Officer", 18, 14.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new StunBaton());
		return list;
	}

}
