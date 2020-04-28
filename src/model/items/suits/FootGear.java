package model.items.suits;

import model.Actor;

public abstract class FootGear extends SuitItem {
    public FootGear(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.FEET_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
