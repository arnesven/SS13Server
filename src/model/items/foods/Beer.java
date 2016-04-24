package model.items.foods;

import graphics.Sprite;
import model.Actor;

public class Beer extends Alcohol {

	public Beer() {
		super("Beer", 0.3, 2);
	}

	@Override
	public FoodItem clone() {
		return new Beer();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("beer", "drinks.png", 7, 4);
    }
}
