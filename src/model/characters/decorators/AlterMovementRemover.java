package model.characters.decorators;

import java.util.NoSuchElementException;

import model.characters.GameCharacter;

public class AlterMovementRemover implements InstanceRemover {

	@Override
	public GameCharacter removeInstance(GameCharacter ch) {
		if (ch instanceof AlterMovement) {
			return ((CharacterDecorator)ch).getInner();
		} else if (ch instanceof CharacterDecorator) {
			return removeInstance(((CharacterDecorator)ch).getInner());
		}
		throw new NoSuchElementException("Did not find that instance!");
	}

}
