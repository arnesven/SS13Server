package model.items.suits;

import model.Actor;

public abstract class HeadGear extends SuitItem {
    public HeadGear(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.HEAD_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public boolean permitsOver() {
        return true;
    }
}
