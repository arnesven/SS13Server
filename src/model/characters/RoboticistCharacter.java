package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.Chemicals;
import model.items.Flamer;
import model.items.GameItem;
import model.items.Knife;
import model.items.MedKit;

public class RoboticistCharacter extends GameCharacter {

	public RoboticistCharacter() {
		super("Roboticist", 1, 7.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new Flamer());
		return list;
	}

}
