package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class CaptainsHat extends HatItem {

    public CaptainsHat() {
        super("Captain's Hat", 0.1, 20);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("captainshat", "hats.png", 11, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("captainshatworn", "head.png", 8, 6, this);
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
