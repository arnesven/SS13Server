package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class SecOffsVest extends SuitItem {
    public SecOffsVest() {
        super("Vest", 0.5);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("secoffsvest", "suits.png", 2);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("secoffsvestworn", "suit.png", 10);
    }

    @Override
    public SuitItem clone() {
        return new SecOffsVest();
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
