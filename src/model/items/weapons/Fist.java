package model.items.weapons;

import model.items.general.GameItem;
import sounds.Sound;

/**
 * Created by erini02 on 18/12/16.
 */
public class Fist extends Weapon implements BludgeoningWeapon {
    public Fist() {
        super("Fists", 0.5, 0.5, false, 0.0, true, 0);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("punch1");
    }

    @Override
    public GameItem clone() {
        return new Fist();
    }
}
