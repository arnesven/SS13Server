package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PiercingProtection;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;
import model.items.weapons.Weapon;

/**
 * Created by erini02 on 20/11/16.
 */
public class PowerSuit extends SuitItem {
    public PowerSuit() {
        super("Power Suit", 400, 5000);
    }

    @Override
    public SuitItem clone() {
        return new PowerSuit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("powersuit", "mecha.png", 14);
    }

    @Override
    public Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("powersuitworn", "mecha.png", 10);
    }


    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new PowerSuitDecorator(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof PowerSuitDecorator)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof PowerSuitDecorator);
        }
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    private class PowerSuitDecorator extends PiercingProtection {
        public PowerSuitDecorator(GameCharacter character) {
            super(character);
        }

        @Override
        public boolean isEncumbered() {
            return false;
        }



        @Override
        public void beExposedTo(Actor something, Damager damager) {
            if (damager instanceof ColdDamage || damager instanceof AsphyxiationDamage) {
                return;
            }
            super.beExposedTo(something, damager);
        }

        @Override
        public Weapon getDefaultWeapon() {
            return Weapon.STEEL_PROD;
        }
    }

    @Override
    public boolean canBePickedUp() {
        return false;
    }
}
