package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.ChimpAppearanceDecorator;

public class ChimpAppearance extends Mutation {

	public ChimpAppearance() {
		super("Chimp Appearance");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new ChimpAppearanceDecorator(forWhom);
	}

}
