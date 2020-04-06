package model.items.suits;

import model.Actor;

public abstract class GlovesItem extends SuitItem {
    public GlovesItem(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    protected int getEquipmentSlot() {
        return Equipment.HANDS_SLOT;
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
