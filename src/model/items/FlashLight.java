package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 17/09/17.
 */
public class FlashLight extends GameItem {
    public FlashLight() {
        super("Flashlight", 0.3, false, 50);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("flashlight", "lighting.png", 12, 7, this);
    }

    @Override
    public GameItem clone() {
        return new FlashLight();
    }
}
