package model.items.weapons;

import model.items.general.GameItem;

/**
 * Created by erini02 on 19/11/16.
 */
public class Claws extends Weapon {
    public Claws() {
        super("Claws", 0.75, 0.5, false, -1.0, true, 0);
        setCriticalChance(0.0);

    }

    @Override
    public GameItem clone() {
        return new Claws();
    }
}
