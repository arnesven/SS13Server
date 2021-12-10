package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.Damager;
import model.events.damage.NoPressureDamage;
import model.items.suits.CheapOxygenMask;

public class LimitedOxyMaskDecorator extends OxyMaskDecorator {
    private final CheapOxygenMask oxyMask;
    private int uses;

    public LimitedOxyMaskDecorator(GameCharacter character, CheapOxygenMask oxyMask) {
        super(character);
        uses = 3;
        this.oxyMask = oxyMask;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        super.beExposedTo(something, damager);
        if (damager instanceof AsphyxiationDamage || damager instanceof NoPressureDamage) {
            uses--;
        }
        if (uses == 0) {
            oxyMask.removeYourself(getActor().getCharacter().getEquipment());
            getActor().addTolastTurnInfo("The " + oxyMask.getBaseName() + " ran out of oxygen. You removed it.");
        }

    }
}
