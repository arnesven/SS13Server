package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 28/11/16.
 */
public class Orange extends HealingFood {
    public Orange(Actor maker) {
        super("Orange", 0.3, maker, 15);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("orange", "harvest.png", 8, 3, this);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    public FoodItem clone() {
        return new Orange(getMaker());
    }
}
