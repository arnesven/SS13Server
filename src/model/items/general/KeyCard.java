package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.map.doors.ElectricalDoor;

public class KeyCard extends GameItem {

	public KeyCard() {
		super("Key Card", 0.1, 129);
	}

	@Override
	public KeyCard clone() {
		return new KeyCard();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("keycard", "card.png", 2, this);
    }

	public boolean canOpenDoor(ElectricalDoor door) {
		return true;
	}

	public static KeyCard findKeyCard(Actor forWhom) {
		for (GameItem it : forWhom.getItems()) {
			if (it instanceof KeyCard) {
				return (KeyCard) it;
			}
		}
		return null;
	}
}
