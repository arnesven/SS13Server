package model.items.weapons;

import model.items.general.GameItem;

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
}
