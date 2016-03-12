package model.items.suits;

import model.Actor;


public class Clothes extends SuitItem {

	public Clothes() {
		super("Clothes", 0.5);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {  }

	@Override
	public void beingTakenOff(Actor actionPerformer) {	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
