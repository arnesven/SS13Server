package model.items.weapons;

import graphics.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class LaserSword extends Weapon {
    public LaserSword() {
        super("Laser Sword", 0.9, 1.0, false, 0.5, true);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("laserswordinhand", "items_righthand.png", 37, 37);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("lasersword", "weapons.png", 1);
    }
}
