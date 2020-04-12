package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class RawFoodContainer extends GameItem {

    public RawFoodContainer() {
        super("Raw Food Container", 1.0, false, 10);
    }

    @Override
    public GameItem clone() {
        return new RawFoodContainer();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("rawfoodcontainer", "storage.png", 13, this);
    }
}
