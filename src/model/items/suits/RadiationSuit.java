package model.items.suits;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.RadiationProtection;

public class RadiationSuit extends SuitItem {

	public RadiationSuit() {
		super("Radiation Suit", 2.0);
	}

	@Override
	public RadiationSuit clone() {
		return new RadiationSuit();
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new RadiationProtection(actionPerformer.getCharacter()));
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof RadiationProtection;
			}
		});
		
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

}
