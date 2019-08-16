package model.mutations;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;

public class HorrorAppearance extends Mutation {

	public HorrorAppearance() {
		super("Horror Appearance");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Horror Appearance") {
            @Override
            public Sprite getSprite(Actor whosAsking) {
                return new Sprite("horrorappearance", "alien.png", 0, this);
            }

            @Override
            public String getPublicName() {
                return "Stalking Horror";
            }
        };
	}

}
