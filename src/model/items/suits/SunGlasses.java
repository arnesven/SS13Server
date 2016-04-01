package model.items.suits;

import java.util.NoSuchElementException;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.CoolDecorator;
import model.characters.decorators.InstanceRemover;

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
		InstanceRemover instRem = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof CoolDecorator) {
					return ((CoolDecorator) ch).getInner();
				} else if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator) ch).getInner());
				}
				throw new NoSuchElementException("Did not find an instance of CoolDecorator");
			}
		};
		
		actionPerformer.removeInstance(instRem);
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
