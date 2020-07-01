package model.characters.decorators;

import model.GameData;
import model.characters.general.GameCharacter;
import model.events.ambient.SmokeEvent;

public class SmokeDegradedVisionDecorator extends DegradedVisionDecorator {
    public SmokeDegradedVisionDecorator(GameCharacter inner) {
        super(inner, "You can't see properly through the smoke!");
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        if (!SmokeEvent.roomAlreadyHasSmoke(getPosition())) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
