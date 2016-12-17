package model.items.weapons;

import sounds.Sound;

/**
 * Created by erini02 on 18/12/16.
 */
public class Fist extends Weapon {
    public Fist() {
        super("Fists", 0.5, 0.5, false, 0.0, true, 0);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("http://www.ida.liu.se/~erini02/ss13/punch1.ogg");
    }
}
