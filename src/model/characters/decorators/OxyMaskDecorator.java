package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.Damager;
import model.events.damage.NoPressureDamage;

/**
 * Created by erini02 on 30/04/16.
 */
public class OxyMaskDecorator extends CharacterDecorator {

    public OxyMaskDecorator(GameCharacter chara) {
        super(chara, "Oxy Mask");
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof NoPressureDamage) {
            super.beExposedTo(something, new AsphyxiationDamage(getActor().getAsTarget(), 1.0));

        } else if (damager instanceof AsphyxiationDamage) {
            return;
        } else {
            super.beExposedTo(something, damager);
        }
    }
}
