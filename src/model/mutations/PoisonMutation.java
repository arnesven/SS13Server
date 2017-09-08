package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PoisonedDecorator;

/**
 * Created by erini02 on 08/09/17.
 */
public class PoisonMutation extends Mutation {
    public PoisonMutation() {
        super("Poison");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new PoisonedDecorator(forWhom.getCharacter(), null);
    }
}
