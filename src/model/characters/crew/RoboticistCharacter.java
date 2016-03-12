package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Tools;
import model.items.weapons.Flamer;
import model.items.weapons.Knife;

public class RoboticistCharacter extends GameCharacter {

	public RoboticistCharacter() {
		super("Roboticist", 1, 7.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Tools());
		list.add(new Flamer());
		return list;
	}

}
