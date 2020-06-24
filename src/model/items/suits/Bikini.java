package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class Bikini extends TorsoSuit {
    public Bikini() {
        super("Bikini", 0.1, 139);
    }

    @Override
    public SuitItem clone() {
        return new Bikini();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bikini", "uniforms.png", 1, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("bikiniworn", "uniform2.png", 21, 21, this);
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
