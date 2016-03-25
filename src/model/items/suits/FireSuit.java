package model.items.suits;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.FireProtection;
import model.characters.decorators.InstanceRemover;

public class FireSuit extends SuitItem {

	public FireSuit() {
		super("Fire suit", 2.0);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new FireProtection(actionPerformer.getCharacter()));
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		InstanceRemover fireProtectionRemover = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof FireProtection) {
					return ((CharacterDecorator)ch).getInner();
				}
				if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				
				throw new NoSuchElementException("Did not find that instance!");
			}
		};
		
		actionPerformer.removeInstance(fireProtectionRemover);
		
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public FireSuit clone() {
		return new FireSuit();
	}
	

}
