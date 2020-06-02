package model.items.weapons;

import model.events.animation.AnimatedSprite;
import model.items.general.GameItem;

import java.awt.*;

/**
 * Created by erini02 on 06/09/17.
 */
public class ElectricShocks extends Weapon {
    public ElectricShocks() {
        super("Electric shock", 0.9, 1.0, false, 0.0, false, 0);
    }

    @Override
    public GameItem clone() {
        return new ElectricShocks();
    }


    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite anms = new AnimatedSprite("electricshocks", "laser.png",
                0, 5, 32, 32, null, 15, false);
        anms.setColor(Color.YELLOW);
        return anms;
    }
}
