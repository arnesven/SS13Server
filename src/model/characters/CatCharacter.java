package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.GameItem;


public class CatCharacter extends GameCharacter {

	public CatCharacter() {
		super("Cat", 20, -5.0);
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return '&';
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
