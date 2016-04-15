package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.ColdDamage;
import model.events.Damager;

/**
 * Created by erini02 on 13/04/16.
 */
public class ColdProtection extends CharacterDecorator {
    public ColdProtection(GameCharacter character) {
        super(character, "Cold protect");
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof ColdDamage) {
            getActor().addTolastTurnInfo("Mmm, warm and cozy.");
            return;
        }
        super.beExposedTo(something, damager);
    }
}
