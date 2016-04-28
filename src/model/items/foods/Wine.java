package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;

public class Wine extends Alcohol {

	public Wine() {
		super("Wine", 1.0, 2);
	}

	@Override
	public FoodItem clone() {
		return new Wine();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wine", "drinks.png", 7, 10);
    }
}
