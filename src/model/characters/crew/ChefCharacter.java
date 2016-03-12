package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.FireExtinguisher;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Knife;

public class ChefCharacter extends GameCharacter {

	public ChefCharacter() {
		super("Chef", 8, 5.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
//		list.add(new FireExtinguisher());
		list.add(new Knife());
		return list;
	}

}
