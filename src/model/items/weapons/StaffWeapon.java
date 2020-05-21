package model.items.weapons;

import graphics.sprites.Sprite;

public abstract class StaffWeapon extends AmmoWeapon {
    public StaffWeapon(String string, boolean bang) {
        super(string, 0.9, 1.0, bang, 1.0, 12, 1949);
        setCriticalChance(0.0);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("firestaffhandheld", "items_righthand.png", 29, 36, this);
    }

}
