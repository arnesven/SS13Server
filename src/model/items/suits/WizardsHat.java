package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class WizardsHat extends HatItem {
    public WizardsHat() {
        super("Wizard's Hat", 0.35, 139);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wizardshat", "hats.png", 5, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("wizardshatworn", "head.png", 7, 4, this);
    }

    @Override
    public SuitItem clone() {
        return new WizardsHat();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }
}
