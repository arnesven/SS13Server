package model.characters.decorators;

import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.PoisonDamage;

/**
 * Created by erini02 on 29/11/16.
 */
public class PoisonedDecorator extends CharacterDecorator {
    public PoisonedDecorator(GameCharacter character) {
        super(character, "poisoned");
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (poisoned)";
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        getActor().addTolastTurnInfo("You don't feel so well...");
        getActor().getAsTarget().beExposedTo(null, new PoisonDamage(0.5));
    }
}
