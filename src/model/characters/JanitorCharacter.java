package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Chemicals;
import model.items.FireExtinguisher;
import model.items.GameItem;
import model.items.Knife;
import model.items.MedKit;

public class JanitorCharacter extends GameCharacter {

	public JanitorCharacter() {
		super("Janitor", 23, 6.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new FireExtinguisher());
		return list;
	}

}
