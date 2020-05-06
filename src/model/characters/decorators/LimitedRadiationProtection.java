package model.characters.decorators;

import model.GameData;
import model.characters.general.GameCharacter;

public class LimitedRadiationProtection extends RadiationProtection {
    private final int roundSet;
    private final int duration;

    public LimitedRadiationProtection(GameCharacter character, int round, int duration) {
        super(character);
        this.roundSet = round;
        this.duration = duration;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (gameData.getRound() > roundSet + duration) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
            getActor().addTolastTurnInfo("The effect of the iodine pill has worn off.");
        }

    }
}
