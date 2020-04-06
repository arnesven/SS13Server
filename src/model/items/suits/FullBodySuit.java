package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

import java.util.Map;

public abstract class FullBodySuit extends SuitItem {
    public FullBodySuit(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    protected int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return true; // blocks all slots
    }

    @Override
    public boolean hasAdditionalSprites() {
        return true;
    }

    @Override
    public Map<Integer, Sprite> getAdditionalSprites() {
        return getFullBodySprites();
    }

    protected abstract Map<Integer,Sprite> getFullBodySprites();
}
