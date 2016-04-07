package model.items.suits;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.FireProtection;

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
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof FireProtection;
			}
		});
		
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
