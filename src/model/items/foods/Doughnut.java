package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;

/**
 * Created by erini02 on 01/05/16.
 */
public class Doughnut extends HealingFood {
    public Doughnut(Actor maker) {
        super("Doughnut", 0.1, maker, 5);
    }

    @Override
    public double getFireRisk() {
        return 0.1;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("doughnut", "food.png", 6);
    }

    @Override
    public FoodItem clone() {
        return new Doughnut(getMaker());
    }


}
