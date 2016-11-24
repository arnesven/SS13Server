package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PiercingProtection;
import model.characters.general.GameCharacter;
import model.events.damage.*;
import model.items.weapons.Weapon;
import model.map.Room;
import model.objects.general.GameObject;
import model.objects.general.Repairable;

/**
 * Created by erini02 on 20/11/16.
 */
public class PowerSuit extends SuitItem {
    private double suitHealth;

    public PowerSuit() {
        super("Power Suit", 400, 5000);
        suitHealth = 2.0;
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
        public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
            double healthBefore = performingClient.getCharacter().getHealth();

            boolean success = super.beAttackedBy(performingClient, weapon);

            double healthAfter = performingClient.getCharacter().getHealth();
            if (healthAfter < healthBefore) {
                suitTakeDamage(healthBefore - healthAfter, performingClient);
            }
            return success;
        }

        @Override
        public void beExposedTo(Actor something, Damager damager) {
            if (damager instanceof ColdDamage || damager instanceof AsphyxiationDamage) {
                return;
            }
             double healthBefore = something.getCharacter().getHealth();

            super.beExposedTo(something, damager);

            if (something.getCharacter().getHealth() < healthBefore
                    && damager instanceof ExplosiveDamage) {
                suitTakeDamage(healthBefore - something.getCharacter().getHealth(), something);
            }
        }

        @Override
        public Weapon getDefaultWeapon() {
            return Weapon.STEEL_PROD;
        }
    }

    private void suitTakeDamage(double v, Actor whosWearing) {
        suitHealth = Math.max(0.0, suitHealth - v);

        if (suitHealth == 0) {
            whosWearing.takeOffSuit();
            whosWearing.getPosition().addObject(new BrokenPowerSuitObject(whosWearing.getPosition()));
        }

    }

    @Override
    public boolean canBePickedUp() {
        return false;
    }

    private class BrokenPowerSuitObject extends GameObject {
        public BrokenPowerSuitObject(Room position) {
            super("Power Suit (broken)", position);
        }

        @Override
        public Sprite getSprite(Player whosAsking) {
            return new Sprite("brokenpowersuit", "mecha.png", 15);
        }
    }
}