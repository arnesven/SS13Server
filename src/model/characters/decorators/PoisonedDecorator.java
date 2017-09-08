package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.PoisonDamage;

/**
 * Created by erini02 on 29/11/16.
 */
public class PoisonedDecorator extends CharacterDecorator {
    private final Actor maker;

    public PoisonedDecorator(GameCharacter character, Actor maker) {
        super(character, "poisoned");
        this.maker = maker;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (poisoned)";
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        //getActor().addTolastTurnInfo("You don't feel so well...");
        getActor().getAsTarget().beExposedTo(maker, new PoisonDamage(0.5));
    }
}
