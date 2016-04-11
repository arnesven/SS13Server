package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.HuskDecorator;

public class HuskMutation extends Mutation {

	public HuskMutation() {
		super("Husk");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new HuskDecorator(forWhom.getCharacter());
	}

}
