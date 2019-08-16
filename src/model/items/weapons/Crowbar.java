package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 18/11/16.
 */
public class Crowbar extends BluntWeapon {
    public Crowbar() {
        super("Crowbar", 1.4, 20, 0.999);
    }

    @Override
    public GameItem clone() {
        return new Crowbar();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("crowbar", "items.png", 48, this);
    }
}
