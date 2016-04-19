package model.mutations;

import model.Actor;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.NameAddDecorator;

public class BlueMutation extends Mutation {

	public BlueMutation() {
		super("Blue");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new NameAddDecorator(forWhom.getCharacter(), "Blue") {
            @Override
            public char getIcon(Player whosAsking) {
                return '_';
            }
        };
	}

}
