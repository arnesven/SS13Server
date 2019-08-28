package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PoisonedDecorator;

/**
 * Created by erini02 on 08/09/17.
 */
public class PoisonMutation extends Mutation {
    private final Actor maker;

    public PoisonMutation(Actor maker) {
        super("Poison");
        this.maker = maker;
    }

    public PoisonMutation() {
        this(null);
    }


    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new PoisonedDecorator(forWhom.getCharacter(), maker);
    }
}
