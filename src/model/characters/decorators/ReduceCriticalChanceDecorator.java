package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.items.weapons.Weapon;
import util.MyRandom;

/**
 * Created by erini02 on 18/12/16.
 */
public class ReduceCriticalChanceDecorator extends CharacterDecorator {
    public ReduceCriticalChanceDecorator(GameCharacter character) {
        super(character, "reduced crit chance");
    }

    @Override
    public boolean wasCriticalHit(Weapon weapon) {
        return super.wasCriticalHit(weapon) && MyRandom.nextDouble() > 0.5;
    }
}
