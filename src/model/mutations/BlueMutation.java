package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.NameAddDecorator;

public class BlueMutation extends Mutation {

	public BlueMutation() {
		super("Blue");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new NameAddDecorator(forWhom.getCharacter(), "Blue");
	}

}
