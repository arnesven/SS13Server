package model.mutations;

import model.Actor;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;

public class SuperSprint extends Mutation {

	public SuperSprint() {
		super("Super Sprint");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new AlterMovement(forWhom.getCharacter(), "Super Sprint", false, 3);
	}

}
