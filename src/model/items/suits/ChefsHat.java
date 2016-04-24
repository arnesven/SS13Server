package model.items.suits;

import graphics.Sprite;
import model.Actor;
import model.items.suits.SuitItem;

public class ChefsHat extends SuitItem {

	public ChefsHat() {
		super("Chef's Hat", 0.2);
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("chefshat", "hats.png", 14);
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
