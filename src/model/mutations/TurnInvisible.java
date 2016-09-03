package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 03/09/16.
 */
public class TurnInvisible extends Mutation {
    public TurnInvisible() {
        super("Invisible");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new CharacterDecorator(forWhom.getCharacter(), "Invisible") {
            @Override
            public boolean isVisible() {
                return false;
            }
        };
    }
}
