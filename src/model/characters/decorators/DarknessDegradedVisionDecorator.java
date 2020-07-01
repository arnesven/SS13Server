package model.characters.decorators;

import model.characters.general.GameCharacter;

public class DarknessDegradedVisionDecorator extends DegradedVisionDecorator {
    public DarknessDegradedVisionDecorator(GameCharacter character) {
        super(character, "It's too dark to see!");
    }
}
