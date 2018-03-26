package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 05/12/16.
 */
public class SickOfYourselfGoal extends PersonalGoal {
    private GameCharacter oldChar;

    @Override
    public String getText() {
        return "You're sick of your life. Change your job or kill yourself.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        oldChar = belongingTo.getInnermostCharacter();
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().isDead() || oldChar != getBelongsTo().getInnermostCharacter();
    }
}
