package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/11/16.
 */
public class SantaSuit extends SuitItem {
    public SantaSuit() {
        super("Santa suit", 1.0, 100);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(new Sprite("santarobe", "suit2.png", 22, 6));

        return new Sprite("santahat", "head.png", 0, 5, 32, 32, sprs);
    }

    @Override
    public SuitItem clone() {
        return new SantaSuit();
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
