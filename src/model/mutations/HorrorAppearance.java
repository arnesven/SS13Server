package model.mutations;

import model.Actor;
import model.Player;
import model.characters.decorators.CharacterDecorator;

public class HorrorAppearance extends Mutation {

	public HorrorAppearance() {
		super("Horror Appearance");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Horror Appearance") {
			@Override
			public char getIcon(Player whosAsking) {
				return 'A';
			}
		};
	}

}
