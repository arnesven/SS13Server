package model.mutations;

import model.Actor;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;

public class SluggishMutation extends Mutation {

	public SluggishMutation() {
		super("Sluggish");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new AlterMovement(forWhom.getCharacter(), "Sluggish", false, 1);
	}

}
