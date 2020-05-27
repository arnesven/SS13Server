package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class FancyGloves extends GlovesItem {
    public FancyGloves() {
        super("Fancy Gloves", 0.05, 109);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("fancygloves", "gloves.png", 1, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("fancyglovesworn", "hands.png", 0, 3, this);
    }

    @Override
    public SuitItem clone() {
        return new FancyGloves();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }
}
