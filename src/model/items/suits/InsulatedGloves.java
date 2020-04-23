package model.items.suits;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.ElectricalDamage;

public class InsulatedGloves extends GlovesItem {
    public InsulatedGloves() {
        super("Insulated Gloves", 0.1, 260);
    }

    @Override
    public SuitItem clone() {
        return new InsulatedGloves();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new ElectricDamageProtection(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ElectricDamageProtection)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof ElectricDamageProtection);
        }
    }

    private class ElectricDamageProtection extends CharacterDecorator {
        public ElectricDamageProtection(GameCharacter character) {
            super(character, "Electric Damage protection");
        }

        @Override
        public void beExposedTo(Actor something, Damager damager) {
            if (damager instanceof ElectricalDamage) {
                getActor().addTolastTurnInfo("You see sparks, but your gloves protect you.");
            } else {
                super.beExposedTo(something, damager);
            }
        }
    }
}
