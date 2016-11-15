package model.items.laws;

import model.Actor;
import model.items.suits.SuitItem;

/**
 * Created by erini02 on 26/10/16.
 */
public class AISuit extends SuitItem {
    public AISuit() {
        super("Your laws are...", 0.0, 0);
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
