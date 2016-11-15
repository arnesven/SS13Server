package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.PiercingProtection;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 26/04/16.
 */
public class SecOffsVest extends SuitItem {
    public SecOffsVest() {
        super("Vest", 1.0, 139);
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
        actionPerformer.setCharacter(new PiercingProtection(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof PiercingProtection;
            }
        });

    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
