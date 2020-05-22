package model.mutations;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

public class InvisibilityDecorator extends CharacterDecorator {

    public InvisibilityDecorator(Actor forWhom) {
        super(forWhom.getCharacter(), "Invisible");
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return false;
    }

}
