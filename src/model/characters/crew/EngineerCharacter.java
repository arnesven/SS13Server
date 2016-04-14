package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.general.GameItem;
import model.items.general.PowerRadio;
import model.items.general.Tools;
import model.items.suits.FireSuit;

public class EngineerCharacter extends CrewCharacter {

	public EngineerCharacter() {
		super("Engineer", 26, 10.0);
	}
	

	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new FireSuit());
		list.add(new Tools());
		list.add(new PowerRadio());
        return list;
	}


	@Override
	public GameCharacter clone() {
		return new EngineerCharacter();
	}
}
