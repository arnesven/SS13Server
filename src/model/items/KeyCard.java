package model.items;

import model.Actor;

public class KeyCard extends GameItem {



	public KeyCard() {
		super("Keycard", 0.1);
	}

	@Override
	public KeyCard clone() {
		return new KeyCard();
	}

}
