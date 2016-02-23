package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;


public class CatCharacter extends GameCharacter {

	public CatCharacter() {
		super("Cat", 20, -5.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

}
