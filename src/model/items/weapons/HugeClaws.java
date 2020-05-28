package model.items.weapons;

import model.items.general.GameItem;

public class HugeClaws extends SlashingWeapon {
    public HugeClaws() {
        super("Huge Claws", 0.75, 1.0, false, 0.0, true, 0);
    }

    @Override
    public GameItem clone() {
        return new HugeClaws();
    }
}
