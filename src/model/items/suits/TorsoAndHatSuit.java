package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

import java.util.Map;

public abstract class TorsoAndHatSuit extends SuitItem {
    public TorsoAndHatSuit(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return targetSlot == Equipment.HEAD_SLOT;
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public boolean hasAdditionalSprites() {
        return true;
    }

    @Override
    public Map<Integer, Sprite> getAdditionalSprites() {
        return getExtraSprites();
    }

    protected abstract Map<Integer,Sprite> getExtraSprites();
}
