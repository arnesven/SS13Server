package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;

/**
 * Created by erini02 on 17/09/17.
 */
public class ExplosiveProtection extends CharacterDecorator {
    public ExplosiveProtection(GameCharacter character) {
        super(character, "explosiveprotection");
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (!(damager instanceof ExplosiveDamage)) {
            super.beExposedTo(something, damager);
        }
    }
}
