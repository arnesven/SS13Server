package model.items.suits;

import java.util.NoSuchElementException;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.FireProtection;
import model.characters.decorators.InstanceRemover;
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
	InstanceRemover radProtectionRemover = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof RadiationProtection) {
					return ((CharacterDecorator)ch).getInner();
				}
				if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				
				throw new NoSuchElementException("Did not find that instance!");
			}
		};
		
		actionPerformer.removeInstance(radProtectionRemover);
		
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

}
