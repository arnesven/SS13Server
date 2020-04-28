package model.items.laws;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.suits.Equipment;
import model.items.suits.SuitItem;
import model.items.suits.TorsoSuit;

/**
 * Created by erini02 on 26/10/16.
 */
public class AISuit extends SuitItem {
    public AISuit() {
        super("No Equipment", 0.0, 0);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("blockedcross", "interface.png", 8, 2, null);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("blockedcross", "interface.png", 8, 2, null);
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.HEAD_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return true;
    }

    @Override
    public SuitItem clone() {
        return new AISuit();
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
