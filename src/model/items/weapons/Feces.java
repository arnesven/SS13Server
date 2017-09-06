package model.items.weapons;

import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Feces extends Weapon {


    public Feces() {
        super("Feces", 1.0, 0.0, false, 0.0, 0);
    }

    @Override
    public GameItem clone() {
        return new Feces();
    }
}
