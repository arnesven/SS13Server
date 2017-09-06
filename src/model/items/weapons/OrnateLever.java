package model.items.weapons;

import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class OrnateLever extends BluntWeapon {
    public OrnateLever() {
        super("Ornate Lever", 1.0, 0, 0.99);
    }

    @Override
    public GameItem clone() {
        return new OrnateLever();
    }
}
