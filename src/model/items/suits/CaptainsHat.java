package model.items.suits;

import graphics.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class CaptainsHat extends SuitItem {
    private CaptainsHat() {
        super("Captain's Hat", 0.1);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("captainshat", "hats.png", 11);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("captainshatworn", "head.png", 8, 6);
    }

    @Override
    public SuitItem clone() {
        return new CaptainsHat();
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
