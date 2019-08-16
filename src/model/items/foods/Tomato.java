package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 27/11/16.
 */
public class Tomato extends HealingFood {
    public Tomato(Actor maker) {
        super("Tomato", 0.2, maker, 2);

    }

    @Override
    public double getFireRisk() {
        return 0;
    }



    @Override
    public FoodItem clone() {
        return new Tomato(getMaker());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("tomatosmall", "harvest.png", 1, 2, this);
    }
}
