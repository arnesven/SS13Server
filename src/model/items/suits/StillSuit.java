package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;

import java.util.ArrayList;

public class StillSuit extends SuitItem {
    public StillSuit() {
        super("Stillsuit", 1.0, 500);
    }

    @Override
    public SuitItem clone() {
        return new StillSuit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {

        return new Sprite("stillsuit", "suits.png", 18, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        ArrayList<Sprite> list = new ArrayList<Sprite>();
        list.add(new RegularBlackShoesSprite());
        list.add(new Sprite("blackmask", "mask.png", 7, 11, this));
        return new Sprite("stillsuitworn", "suit2.png", 0, 20, 32, 32, list, this);

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
