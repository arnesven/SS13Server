package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

import java.util.Map;

public abstract class TorsoAndShoesSuit extends SuitItem {
    public TorsoAndShoesSuit(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    protected int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public boolean permitsOver() {
        return true;
    }

    @Override
    public boolean hasAdditionalSprites() {
        return true;
    }

    @Override
    public Map<Integer, Sprite> getAdditionalSprites() {
        return getOtherSprites();
    }

    protected abstract Map<Integer,Sprite> getOtherSprites();
}
