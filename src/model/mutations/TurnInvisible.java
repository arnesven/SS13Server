package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;

/**
 * Created by erini02 on 03/09/16.
 */
public class TurnInvisible extends Mutation {
    public TurnInvisible() {
        super("Invisible");

    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new InvisibilityDecorator(forWhom);
    }

}
