package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class Mankini extends TorsoSuit {
    public Mankini() {
        super("Mankini", 0.1, 149);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("mankiniworn", "uniform2.png", 2, 24, this);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("mankini", "uniforms.png", 1, this);
    }

    @Override
    public SuitItem clone() {
        return new Mankini();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.getCharacter().getPhysicalBody().setWithUnderwear(false);
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.getCharacter().getPhysicalBody().setWithUnderwear(true);
    }

    @Override
    public boolean permitsOver() {
        return true;
    }
}
