package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.SecurityRadio;
import model.items.weapons.StunBaton;

public class SecurityOfficerCharacter extends CrewCharacter {

	public SecurityOfficerCharacter() {
		super("Security Officer", 18, 14.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new StunBaton());
		list.add(new SecurityRadio());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new SecurityOfficerCharacter();
	}

}
