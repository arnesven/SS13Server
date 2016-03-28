package model.items.suits;

import model.Actor;

public class JumpSuit extends SuitItem {

	public JumpSuit() {
		super("Jump Suit", 0.5);
	}

	@Override
	public JumpSuit clone() {
		return new JumpSuit();
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
