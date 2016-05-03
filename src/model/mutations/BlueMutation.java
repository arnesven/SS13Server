package model.mutations;

import graphics.sprites.Sprite;
import model.Actor;
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
            public Sprite getNakedSprite() {
                return new Sprite("blueguy", "genetics.png", 4);
            }
        };
	}

}
