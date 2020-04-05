package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;

public class StunnedDecorator extends AlterMovement {

    public StunnedDecorator(GameCharacter character) {
        super(character, "Stunned", true, 0);
    }

}
