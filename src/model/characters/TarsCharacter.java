package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;

public class TarsCharacter extends GameCharacter {

	public TarsCharacter() {
		super("TARS", 0, 20.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<GameItem>();
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
}
