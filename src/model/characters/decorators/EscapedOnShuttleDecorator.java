package model.characters.decorators;

import model.characters.general.GameCharacter;

public class EscapedOnShuttleDecorator extends CharacterDecorator {
    public EscapedOnShuttleDecorator(GameCharacter character) {
        super(character, "EscapedOnShuttle");
    }


    @Override
    public boolean getsActions() {
        return false;
    }

    @Override
    public boolean isPassive() {
        return true;
    }
}
