package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.BleedingDamager;
import model.events.damage.InternalBleeding;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.objects.general.BloodPool;

public class BleedingDecorator extends CharacterDecorator {
    private final Actor perp;

    public BleedingDecorator(GameCharacter character, Actor perp) {
        super(character, "Bleeding");
        this.perp = perp;
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        getActor().getCharacter().beExposedTo(perp, new BleedingDamager());
        getActor().getPosition().addObject(new BloodPool(getActor().getPosition()));
    }

    @Override
    public boolean hasSpecificReaction(GameItem it) {
        if (it instanceof MedKit) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
            getActor().addTolastTurnInfo("The bleeding has stopped.");
        }
        return false;
    }
}
