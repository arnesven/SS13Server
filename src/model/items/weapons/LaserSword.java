package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 26/04/16.
 */
public class LaserSword extends SlashingWeapon {
    public LaserSword() {
        super("Laser Sword", 0.9, 1.0, false, 0.5, true, 399);
        this.setCriticalChance(0.15);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("laserswordinhand", "items_righthand.png", 37, 37, this);
    }

    @Override
    public GameItem clone() {
        return new LaserSword();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("lasersword", "weapons.png", 1, this);
    }
}
