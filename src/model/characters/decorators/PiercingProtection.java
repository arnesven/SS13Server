package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;
import model.items.general.Grenade;
import model.items.weapons.*;

/**
 * Created by erini02 on 01/05/16.
 */
public class PiercingProtection extends CharacterDecorator {
    public PiercingProtection(GameCharacter character) {
        super(character, "piercing protection");
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
        double oldHealth = getActor().getCharacter().getHealth();

        if (getProtectionFrom(weapon)) {
            getActor().getCharacter().setHealth(oldHealth + 0.5);
            getActor().addTolastTurnInfo("Your armor protects you.");
        }
        boolean res = super.beAttackedBy(performingClient, weapon, gameData);
        if (!performingClient.isDead()) {
            if (getProtectionFrom(weapon)) {
                if (getActor().getCharacter().getHealth() == oldHealth + 0.5) {
                    getActor().getCharacter().setHealth(oldHealth - 0.5);
                }
            }
        }

        return res;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        double oldHealth = getActor().getCharacter().getHealth();
        super.beExposedTo(something, damager);
        if (getProtectionFrom(damager)) {
            if (oldHealth > getActor().getCharacter().getHealth() && !getActor().isDead()) {
                // get refunded some health
                getActor().addToHealth(0.5);
                getActor().addTolastTurnInfo("Your armor protects you.");
            }
        }
    }

    private boolean getProtectionFrom(Damager damager) {
        return damager instanceof Grenade || damager instanceof ExplosiveDamage;
    }

    private boolean getProtectionFrom(Weapon weapon) {
        return weapon instanceof PiercingWeapon || weapon instanceof BludgeoningWeapon || weapon instanceof SlashingWeapon;
    }
}
