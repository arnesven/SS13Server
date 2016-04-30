package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.OxyMaskDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 30/04/16.
 */
public class OxygenMask extends SuitItem {
    public OxygenMask() {
        super("Oxygen Mask", 0.5);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("oxymask", "hats.png", 2);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("oxymaskworn", "mask.png", 8);
    }

    @Override
    public SuitItem clone() {
        return new OxygenMask();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new OxyMaskDecorator(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof OxyMaskDecorator;
            }
        });
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
