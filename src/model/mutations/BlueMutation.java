package model.mutations;

import graphics.Sprite;
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
            public Sprite getSprite(Actor whosAsking) {
                return new Sprite("blueguy", "genetics.png", 4);
            }
        };
	}

}
