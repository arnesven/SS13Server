package model.items.suits;


import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.SpaceProtection;

public class SpaceSuit extends SuitItem {

	public SpaceSuit() {
		super("Space suit", 4.0);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new SpaceProtection(actionPerformer.getCharacter()));
		
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		System.out.println("In being taken off.");
//		actionPerformer.getCharacter().printInstances();
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof SpaceProtection;
			}
		});
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public SpaceSuit clone() {
		return new SpaceSuit();
	}

}
