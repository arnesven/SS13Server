package model.modes.goals;

import model.Actor;
import model.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/12/16.
 */
public class AvengerGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Avenge a fallen crew mate.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        List<Actor> youKilled = getKillListFor(getBelongsTo(), gameData);

        for (Actor a : youKilled) {
            List<Actor> victimKilled = getKillListFor(a, gameData);
            if (victimKilled.size() > 1) {
                return true;
            } else if (victimKilled.size() == 1) {
                return victimKilled.get(0) != getBelongsTo();
            }
        }

        return false;
    }

    private List<Actor> getKillListFor(Actor belongsTo, GameData gameData) {
        List<Actor> killList = new ArrayList<>();
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                if (a.getCharacter().getKiller() == belongsTo) {
                    killList.add(a);
                }
            }
        }
        return killList;
    }
}
