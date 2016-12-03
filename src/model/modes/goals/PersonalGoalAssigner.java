package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import util.MyRandom;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by erini02 on 03/12/16.
 */
public class PersonalGoalAssigner {

    private Map<Actor, PersonalGoal> goalsForActors = new HashMap<>();
    private final Collection<PersonalGoal> taskCollection;

    public PersonalGoalAssigner(GameData gameData) {
        taskCollection = new HashSet<>();
        taskCollection.add(new CollectMoneyTask(null));
    }

    public void addTasks(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (taskCollection.size() > 0) {
                if (a instanceof Player) {
                    if (!gameData.getGameMode().isAntagonist((Player) a)) {
                        if (((Player) a).getSettings().get(PlayerSettings.GIVE_ME_A_TASK)) {
                            PersonalGoal pt = MyRandom.sample(taskCollection);
                            taskCollection.remove(pt);
                            pt.setBelongsTo(a);
                            goalsForActors.put(a, pt);
                        }
                    }
                }
            }
        }
    }

    public Map<Actor, PersonalGoal> getGoalsForActors() {
        return goalsForActors;
    }
}
