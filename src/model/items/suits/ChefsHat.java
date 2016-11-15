package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;

public class ChefsHat extends SuitItem {

	public ChefsHat() {
		super("Chef's Hat", 0.2, 10);
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("chefshat", "hats.png", 14);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("chefshatworn", "head.png", 1, 8);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public ChefsHat clone() {
		return new ChefsHat();
	}

}
