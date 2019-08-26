package model.mutations;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 03/05/16.
 */
public class LizardMutation extends Mutation {
    public LizardMutation() {
        super("Reptilian");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new CharacterDecorator(forWhom.getCharacter(), "Lizard") {
            @Override
            public Sprite getNakedSprite() {
                return new Sprite("lizardguy", "genetics.png", 47, getActor());
            }

            @Override
            public String getPublicName() {
                return "Lizardman" + (getActor().isDead()?" (dead)":"");
            }
        };
    }
}
