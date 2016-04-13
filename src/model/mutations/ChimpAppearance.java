package model.mutations;

import model.Actor;
import model.Player;
import model.characters.decorators.CharacterDecorator;

public class ChimpAppearance extends Mutation {

	public ChimpAppearance() {
		super("Chimp Appearance");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Chimp Appearance") {
			@Override
			public char getIcon(Player whosAsking) {
				return ']';
			}
		};
	}

}
