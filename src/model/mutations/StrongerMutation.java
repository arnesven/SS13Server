package model.mutations;

import model.Actor;
import model.characters.decorators.AlterStrength;
import model.characters.decorators.CharacterDecorator;

public class StrongerMutation extends Mutation {

	public StrongerMutation() {
		super("Super Strength");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new AlterStrength(forWhom.getCharacter(), "mutation", false, 10.0);
	}

}
