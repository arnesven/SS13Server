package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.Grenade;
import model.items.KeyCard;
import model.items.NuclearDisc;
import model.items.foods.Beer;
import model.items.suits.FireSuit;

public class CaptainCharacter extends CrewCharacter {

	public CaptainCharacter() {
		super("Captain", 20, 16.0);
	}
	
	@Override
	public List<GameItem> getStartingItems(){
		ArrayList<GameItem> list = new ArrayList<GameItem>();
		list.add(new NuclearDisc());
		list.add(new KeyCard());
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		list.add(new Beer()); // TODO remove, debugging
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new CaptainCharacter();
	}

}
