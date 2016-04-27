package model.items.foods;

import graphics.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class SpaceRum extends Alcohol {
    public SpaceRum() {
        super("Space Rum", 0.75, 3);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spacerum", "drinks.png", 17);
    }

    @Override
    public FoodItem clone() {
        return new SpaceRum();
    }
}
