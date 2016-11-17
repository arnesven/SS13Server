package model.characters.decorators;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 17/11/16.
 */
public class PassiveDecorator extends CharacterDecorator {
    public PassiveDecorator(GameCharacter character) {
        super(character, "is passive");
    }

    @Override
    public boolean isPassive() {
        return true;
    }
}
