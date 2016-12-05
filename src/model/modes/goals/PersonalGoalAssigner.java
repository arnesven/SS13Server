package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import util.MyRandom;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by erini02 on 03/12/16.
 */
public class PersonalGoalAssigner implements Serializable {

    private Map<Actor, PersonalGoal> goalsForActors = new HashMap<>();
    private final Collection<PersonalGoal> taskCollection;

    public PersonalGoalAssigner(GameData gameData) {
        taskCollection = new HashSet<>();
        taskCollection.add(new CollectMoneyTask(MyRandom.nextInt(3)*300 + 900));
        taskCollection.add(new ParasiteKiller(MyRandom.nextInt(3) + 3));
        taskCollection.add(new FireManGoal(MyRandom.nextInt(3) + 3));
        taskCollection.add(new HullBreachGoal(MyRandom.nextInt(2) + 2));
        taskCollection.add(new PlaySlotsGoal(MyRandom.nextInt(2) + 3));
        taskCollection.add(new AvengerGoal());
        taskCollection.add(new CollectThreeChemicals());
        taskCollection.add(new EatDifferentFoods(MyRandom.nextInt(2)+3));
        taskCollection.add(new GoIntoSpaceGoal());
        taskCollection.add(new SickOfYourselfGoal());
        taskCollection.add(new LayerUponLayerGoal(MyRandom.nextInt(4)+5));
        taskCollection.add(new PacifistGoal(MyRandom.nextInt(2)));
        taskCollection.add(new PervertGoal());
        taskCollection.add(new BeatUpTheClownGoal());
        taskCollection.add(new DrinkAlcoholGoal(MyRandom.nextInt(2)+2));
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
                            a.addTolastTurnInfo(pt.getText());
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
