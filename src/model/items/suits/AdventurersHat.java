package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 18/10/16.
 */
public class AdventurersHat extends SuitItem {
    public AdventurersHat() {
        super("Stetson Hat", 0.2);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("stetsonw", "head.png", 10, 5, 32, 32);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("stetson", "hats.png", 6);
    }

    @Override
    public SuitItem clone() {
        return new AdventurersHat();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    public boolean permitsOver() {
        return true;
    }
}
