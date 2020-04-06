package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.ReduceCriticalChanceDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 26/04/16.
 */
public class SecOffsHelmet extends HatItem {
    public SecOffsHelmet() {
        super("Helmet", 0.5, 50);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("secoffshelmet", "hats.png", 1, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("secoffshelmetworn", "head.png", 0, this);
    }

    @Override
    public SuitItem clone() {
        return new SecOffsHelmet();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new ReduceCriticalChanceDecorator(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof ReduceCriticalChanceDecorator);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}
