package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.PiercingProtection;
import model.characters.general.GameCharacter;
import model.events.damage.*;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 20/11/16.
 */
public class PowerSuit extends FaceCoveringSuitItem {
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
    public int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return true;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("powersuit", "mecha.png", 14, this);
    }

    @Override
    public Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("powersuitworn", "mecha.png", 10, this);
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
        public String getPublicName() {
            String str = "Somebody in a power Suit";
            return str;
        }

        @Override
        public boolean isEncumbered() {
            return false;
        }


        @Override
        public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
            double healthBefore = performingClient.getCharacter().getHealth();

            boolean success = super.beAttackedBy(performingClient, weapon, gameData);

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
             double healthBefore = getActor().getCharacter().getHealth();

            super.beExposedTo(something, damager);

            if (getActor().getCharacter().getHealth() < healthBefore
                    && damager instanceof ExplosiveDamage) {
                if (something != null) {
                    suitTakeDamage(healthBefore - something.getCharacter().getHealth(), something);
                }
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
            removeYourself(whosWearing.getCharacter().getEquipment());
            whosWearing.getPosition().addObject(new BrokenPowerSuitObject(whosWearing.getPosition()));
        }

    }

    @Override
    public boolean canBePickedUp() {
        return false;
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "A large mechanical suit which lets the pilot to lift heavy loads. Also protects the pilot a little from harm.";
    }

    private class BrokenPowerSuitObject extends GameObject {
        public BrokenPowerSuitObject(Room position) {
            super("Power Suit (broken)", position);
        }

        @Override
        public Sprite getSprite(Player whosAsking) {
            return new Sprite("brokenpowersuit", "mecha.png", 15, this);
        }
    }
}
