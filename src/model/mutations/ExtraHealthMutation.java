package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.ExtraHealthDecorator;

/**
 * Created by erini02 on 14/04/16.
 */
public class ExtraHealthMutation extends Mutation {

    public ExtraHealthMutation() {
        super("Extra health");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new ExtraHealthDecorator(forWhom.getCharacter(), 1);
    }
}
