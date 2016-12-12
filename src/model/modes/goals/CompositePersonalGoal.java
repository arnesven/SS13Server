package model.modes.goals;

import model.Actor;
import model.GameData;
import util.Logger;

import java.util.List;

/**
 * Created by erini02 on 08/12/16.
 */
public abstract class CompositePersonalGoal extends PersonalGoal {

    private List<PersonalGoal> subGoals;

    protected abstract List<PersonalGoal> initializeGoals();

    public CompositePersonalGoal() {
        subGoals = initializeGoals();
    }

    @Override
    public String getText() {
        StringBuilder bldr = new StringBuilder("");
        for (int i = subGoals.size()-1; i > 0; i--) {
            bldr.append(subGoals.get(i).getText());
            if (i > 1) {
                bldr.append(", ");
            }
        }
        bldr.append(" and " + subGoals.get(0).getText());
        return bldr.toString();
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        for (PersonalGoal g : subGoals) {
            g.setBelongsTo(belongingTo);
        }
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor potential) {
        for (PersonalGoal g : subGoals) {
            if (!g.isApplicable(gameData, potential)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        for (PersonalGoal pg : subGoals) {
            Logger.log("Checking goal " + pg.getText());
            if (!pg.isCompleted(gameData)) {
                Logger.log("  -> not Complete");
                return false;
            }
            Logger.log("  -> COMPLETE");
        }
        return true;
    }
}
