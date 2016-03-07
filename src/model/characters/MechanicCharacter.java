package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Chemicals;
import model.items.FireExtinguisher;
import model.items.Flamer;
import model.items.GameItem;
import model.items.Knife;
import model.items.Tools;

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
