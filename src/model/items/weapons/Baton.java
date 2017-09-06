package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Baton extends BluntWeapon {
    public Baton() {
        super("Baton", 0.5, 15, 1.0);
    }

    @Override
    public GameItem clone() {
        return new Baton();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("regularbaton", "weapons.png", 3);
    }
}
