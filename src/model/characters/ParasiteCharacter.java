package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;


public class ParasiteCharacter extends GameCharacter {

	public ParasiteCharacter() {
		super("Parasite", 0, -1.0);

	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

	
	@Override
	public boolean isCrew() {
		return false;
	}
	
}
