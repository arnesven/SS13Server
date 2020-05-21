package model.items.weapons;

import graphics.sprites.Sprite;

public class WandWeapon extends AmmoWeapon {
    public WandWeapon(String string, double damage) {
        super(string, 0.95, damage, false, 0.5, 8, 1300);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("wandhandheld", "wizardstuff.png", 0, 2, this);
    }

}
