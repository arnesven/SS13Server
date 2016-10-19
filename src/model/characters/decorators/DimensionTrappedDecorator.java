package model.characters.decorators;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 19/10/16.
 */
public class DimensionTrappedDecorator extends CharacterDecorator {
    public DimensionTrappedDecorator(GameCharacter character) {
        super(character, "Trapped in another dimension");
    }

    @Override
    public boolean isPassive() {
        return true;
    }
}
