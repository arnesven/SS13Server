package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.foods.Beer;
import model.items.foods.Vodka;
import model.items.foods.Wine;
import model.items.general.GameItem;
import model.items.weapons.Shotgun;

public class BartenderCharacter extends CrewCharacter {

	public BartenderCharacter() {
		super("Bartender", 10, 4.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Shotgun());
        list.add(new Beer());
        list.add(new Beer());
        list.add(new Vodka());
        list.add(new Wine());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new BartenderCharacter();
	}

}
