package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.items.general.GameItem;

public class NobodyCharacter extends HumanCharacter {

	public NobodyCharacter(int startRoom) {
		super("Nobody", startRoom, -500.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

	@Override
	public GameCharacter clone() {
		return new NobodyCharacter(2);
	}

}
