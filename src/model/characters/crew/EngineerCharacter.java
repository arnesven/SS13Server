package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Tools;
import model.items.suits.FireSuit;
import model.items.weapons.Flamer;
import model.items.weapons.Knife;

public class EngineerCharacter extends GameCharacter {

	public EngineerCharacter() {
		super("Engineer", 26, 10.0);
	}
	

	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new FireSuit());
		list.add(new Tools());
		return list;
	}
}
