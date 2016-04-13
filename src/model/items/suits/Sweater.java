package model.items.suits;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.ColdProtection;
import model.characters.decorators.InstanceChecker;

/**
 * Created by erini02 on 13/04/16.
 */
public class Sweater extends SuitItem {
    public Sweater() {
        super("Sweater", 0.5);
    }

    @Override
    public SuitItem clone() {
        return new Sweater();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new ColdProtection(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof ColdProtection;
            }
        });
    }

    @Override
    public boolean permitsOver() {
        return true;
    }
}
