package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public class BananaPeelItem extends GameItem {
    public BananaPeelItem() {
        super("Banana Peel", 0.01, false, 0);
    }

    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("banana", "items.png", 63, this);
    }

    @Override
    public GameItem clone() {
        return new BananaPeelItem();
    }
}
