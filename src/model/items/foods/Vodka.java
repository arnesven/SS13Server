package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;

public class Vodka extends Alcohol {

	public Vodka() {
		super("Vodka", 0.8, 4, 70);
	}

	@Override
	public FoodItem clone() {
		return new Vodka();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("vodka", "drinks.png", 1, 1);
    }
}
