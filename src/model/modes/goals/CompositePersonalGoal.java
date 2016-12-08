package model.modes.goals;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 08/12/16.
 */
public abstract class CompositePersonalGoal extends PersonalGoal {

    private List<PersonalGoal> subGoals = getGoals();

    protected abstract List<PersonalGoal> getGoals();

    @Override
    public String getText() {
        StringBuilder bldr = new StringBuilder("");
        for (int i = subGoals.size()-1; i > 0; i--) {
            bldr.append(subGoals.get(i));
            if (i > 1) {
                bldr.append(", ");
            }
        }
        bldr.append(" and " + subGoals.get(0));
        return bldr.toString();
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        for (PersonalGoal pg : subGoals) {
            if (!pg.isCompleted(gameData)) {
                return false;
            }
        }
        return true;
    }
}
