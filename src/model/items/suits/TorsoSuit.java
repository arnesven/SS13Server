package model.items.suits;

import model.Actor;

public abstract class TorsoSuit extends SuitItem {
    public TorsoSuit(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

}
