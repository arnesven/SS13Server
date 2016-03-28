package model.items.suits;

import model.Actor;

public class SunGlasses extends SuitItem {

	public SunGlasses() {
		super("Sun Glasses", 0.1);
	}

	@Override
	public SunGlasses clone() {
		return new SunGlasses();
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		for (Actor a : actionPerformer.getPosition().getActors()) {
			if (a != actionPerformer) {
				a.addTolastTurnInfo("The " + actionPerformer.getPublicName() + " is a cool dude" + 
							(actionPerformer.getCharacter().getGender().equals("woman")?"ess":"") + "!");
			}
		}
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
