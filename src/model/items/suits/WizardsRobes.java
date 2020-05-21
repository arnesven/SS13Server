package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class WizardsRobes extends SuitItem {
    public WizardsRobes() {
        super("Wizard's Robes", 1.35, 649);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wizardsrobes", "suits.png", 21, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("wizardsrobeworn", "suit.png", 70, this);
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public SuitItem clone() {
        return new WizardsRobes();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
