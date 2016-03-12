package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.FireExtinguisher;
import model.items.GameItem;
import model.items.Tools;
import model.items.weapons.Flamer;
import model.items.weapons.Knife;

public class MechanicCharacter extends GameCharacter {

	public MechanicCharacter() {
		super("Mechanic", 26, 3.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new FireExtinguisher());
		list.add(new Tools());
		return list;
	}

}
