package model.items.weapons;

import graphics.sprites.Sprite;
import model.events.animation.AnimatedSprite;

import java.awt.*;

public class SlugthrowerWeapon extends AmmoWeapon {
    public SlugthrowerWeapon(String string, double hitChance, double damage, boolean bang, double weight, int shots, int cost) {
        super(string, hitChance, damage, bang, weight, shots, cost);
    }

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite sp = new AnimatedSprite("gunshoteffect", "laser.png", 0, 3, 32, 32, null, 3, false);
        sp.setColor(Color.BLACK);
        return sp;
    }
}
