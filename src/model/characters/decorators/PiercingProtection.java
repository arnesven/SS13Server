package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;
import model.items.general.Grenade;
import model.items.weapons.Knife;
import model.items.weapons.Revolver;
import model.items.weapons.Shotgun;
import model.items.weapons.Weapon;

/**
 * Created by erini02 on 01/05/16.
 */
public class PiercingProtection extends CharacterDecorator {
    public PiercingProtection(GameCharacter character) {
        super(character, "piercing protection");
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
        double oldHealth = getActor().getCharacter().getHealth();
        boolean res = super.beAttackedBy(performingClient, weapon);
        if (getProtectionFrom(weapon)) {
            if (oldHealth > getActor().getCharacter().getHealth()) {
                // get refunded some health
                getActor().addToHealth(0.5);
            }
        }
        return res;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        double oldHealth = getActor().getCharacter().getHealth();
        super.beExposedTo(something, damager);
        if (getProtectionFrom(damager)) {
            if (oldHealth > getActor().getCharacter().getHealth()) {
                // get refunded some health
                getActor().addToHealth(0.5);
            }
        }
    }

    private boolean getProtectionFrom(Damager damager) {
        return damager instanceof Grenade || damager instanceof ExplosiveDamage;
    }

    private boolean getProtectionFrom(Weapon weapon) {
        return weapon instanceof Knife ||
                weapon instanceof Shotgun ||
                weapon instanceof Revolver ||
                weapon == HorrorCharacter.hugeClaw;
    }
}
