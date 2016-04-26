package model.items.suits;

import graphics.Sprite;
import model.Actor;

/**
 * Created by erini02 on 26/04/16.
 */
public class SecOffsHelmet extends SuitItem {
    public SecOffsHelmet() {
        super("Helmet", 0.2);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("secoffshelmet", "hats.png", 1);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("secoffshelmetworn", "head.png", 0);
    }

    @Override
    public SuitItem clone() {
        return new SecOffsHelmet();
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
