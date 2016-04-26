package model.items.general;

import graphics.Sprite;
import model.Actor;

public class TornClothes extends GameItem {

	public TornClothes() {
		super("Torn Clothes", 0.5);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("tornclothes", "uniform2.png", 4);
    }

    @Override
	public GameItem clone() {
		return new TornClothes();
	}

}
