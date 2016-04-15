package model.items.suits;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.decorators.CoolDecorator;
import model.characters.decorators.InstanceChecker;

public class SunGlasses extends SuitItem {

	public SunGlasses() {
		super("Sun Glasses", 0.1);
	}

	@Override
	public SunGlasses clone() {
		return new SunGlasses();
	}
	
	@Override
	protected char getIcon() {
		return 'Z';
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new CoolDecorator(actionPerformer.getCharacter(), this));
		for (Actor a : actionPerformer.getPosition().getActors()) {
			if (a != actionPerformer) {
				a.addTolastTurnInfo("The " + actionPerformer.getPublicName() + " is a cool dude" + 
							(actionPerformer.getCharacter().getGender().equals("woman")?"ss":"") + "!");
			}
		}
	
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof CoolDecorator;
			}
		});
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
