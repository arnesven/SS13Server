package model.items.weapons;

import model.items.general.GameItem;

public class Tusks extends Weapon implements PiercingWeapon {
    public Tusks() {
        super("Tusks", 0.5, 1.0, false, 1.0, true, 0);
    }

    @Override
    public GameItem clone() {
        return new Tusks();
    }
}
