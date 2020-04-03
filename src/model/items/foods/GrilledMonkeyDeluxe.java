package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;

public class GrilledMonkeyDeluxe extends HealingFood {

    public GrilledMonkeyDeluxe(Actor maker) {
        super("Grilled Monkey", 1.5, maker, 1300);
    }

    @Override
    public double getFireRisk() {
        return 0.3;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("grilledmonkeydeluxe", "food.png", 17, 15, this);
    }

    @Override
    public FoodItem clone() {
        return new GrilledMonkeyDeluxe(getMaker());
    }
}
