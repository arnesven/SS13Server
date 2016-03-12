package model.items.suits;

import java.util.NoSuchElementException;

import model.Actor;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.FireProtection;
import model.characters.decorators.InstanceRemover;
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
		InstanceRemover spaceProtectionRemover = new InstanceRemover() {

			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof SpaceProtection) {
					return ((CharacterDecorator)ch).getInner();
				}
				if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}

				throw new NoSuchElementException("Did not find that instance!");
			}
		};
		
		actionPerformer.removeInstance(spaceProtectionRemover);
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

}
