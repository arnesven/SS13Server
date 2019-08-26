package model.mutations;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;

public class ChimpAppearance extends Mutation {

	public ChimpAppearance() {
		super("Chimp Appearance");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Chimp Appearance") {
            @Override
            public Sprite getSprite(Actor whosAsking) {
                return new Sprite("chimpappearance", "monkey.png", 0, getActor());
            }

            @Override
            public String getPublicName() {
                return "Chimp";
            }
        };
	}

}
