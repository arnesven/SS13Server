package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class KeyCard extends GameItem {



	public KeyCard() {
		super("Keycard", 0.1, 129);
	}

	@Override
	public KeyCard clone() {
		return new KeyCard();
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("keycard", "card.png", 1);
    }
}
