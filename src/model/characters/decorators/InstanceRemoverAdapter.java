package model.characters.decorators;

import java.util.NoSuchElementException;

import model.characters.GameCharacter;

public class InstanceRemoverAdapter implements InstanceRemover {

	private InstanceChecker checker;

	public InstanceRemoverAdapter(InstanceChecker instanceChecker) {
		this.checker = instanceChecker;
	}

	@Override
	public GameCharacter removeInstance(GameCharacter ch) {
		if (ch.checkInstance(checker)) {
			return ((CharacterDecorator)ch).getInner();
		} else if (ch instanceof CharacterDecorator) {
			return removeInstance(((CharacterDecorator)ch).getInner());
		}
		throw new NoSuchElementException("Could not find instance!");
	}

}
