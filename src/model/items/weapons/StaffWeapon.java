package model.items.weapons;

import graphics.sprites.Sprite;
import model.events.animation.AnimatedSprite;

import java.awt.*;

public abstract class StaffWeapon extends AmmoWeapon {
    public StaffWeapon(String string, boolean bang) {
        super(string, 0.9, 1.0, bang, 1.0, 12, 1949);
        setCriticalChance(0.0);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("firestaffhandheld", "items_righthand.png", 29, 36, this);
    }


    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite beamSprite = new AnimatedSprite("laserbeamcyan", "laser.png",
                1, 2, 32, 32, null, 7, false);
        beamSprite.setColor(Color.CYAN);
        return beamSprite;
    }
}
