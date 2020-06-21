package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;

public class StunnedDecorator extends AlterMovement implements DisablingDecorator {

    public StunnedDecorator(GameCharacter character) {
        super(character, "Stunned", true, 0);
    }

    @Override
    public double getCombatBaseDefense() {
        return 0.5;
    }
}
