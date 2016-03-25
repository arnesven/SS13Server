package model.items.suits;

import model.Actor;


/**
 * @author erini02
 * This class is no longer in use as far as I know...
 */
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

	@Override
	public Clothes clone() {
		return new Clothes();
	}

}
