package model.characters.crew;

import java.util.ArrayList;

import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.Tools;
import model.items.FireExtinguisher;

public class RoboticistCharacter extends CrewCharacter {

	public RoboticistCharacter() {
		super("Roboticist", 1, 7.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Tools());
		list.add(new FireExtinguisher());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new RoboticistCharacter();
	}

}
