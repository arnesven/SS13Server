package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.characters.crew.DetectiveCharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.modes.GameMode;
import util.MyRandom;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by erini02 on 03/12/16.
 */
public class  PersonalGoalAssigner implements Serializable {
    private Map<Actor, PersonalGoal> goalsForActors = new HashMap<>();
    private final Collection<PersonalGoal> generalGoals;
    private final HashMap<String, Collection<PersonalGoal>> jobSpecificGoals;

    public PersonalGoalAssigner(GameData gameData) {
        generalGoals = createGeneralCoals();

        jobSpecificGoals = newEmptyMap();
        jobSpecificGoals.get(new DetectiveCharacter().getBaseName()).add(new GuessGameModeGoal(MyRandom.nextInt(3)+5));

    }




    public void addTasks(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (generalGoals.size() > 0) {
                if (a instanceof Player) {
                    if (!gameData.getGameMode().isAntagonist((Player) a)) {
                        if (((Player) a).getSettings().get(PlayerSettings.GIVE_ME_A_TASK)) {
                            setGoal(gameData, a);
                        }
                    }
                }
            }
        }
    }


    public Map<Actor, PersonalGoal> getGoalsForActors() {
        return goalsForActors;
    }



    private void setGoal(GameData gameData, Actor a) {

        if (jobSpecificGoals.get(a.getBaseName()).isEmpty() || MyRandom.nextDouble() < 0.50) {
            giveGoalToActorFrom(a, gameData, generalGoals);
        } else {
            giveGoalToActorFrom(a, gameData, jobSpecificGoals.get(a.getBaseName()));
        }
    }

    private void giveGoalToActorFrom(Actor a, GameData gameData, Collection<PersonalGoal> setOfGoals) {
        do {
            PersonalGoal pt = MyRandom.sample(setOfGoals);
            setOfGoals.remove(pt);
            if (pt.isApplicable(gameData, a)) {
                pt.setBelongsTo(a);
                goalsForActors.put(a, pt);
                a.addTolastTurnInfo(pt.getText());
                break;
            }
        } while (setOfGoals.size() > 0);
    }



    private static HashMap<String, Collection<PersonalGoal>> newEmptyMap() {
        HashMap<String, Collection<PersonalGoal>> result = new HashMap<>();
        for (GameCharacter gc : GameMode.getAllCrew()) {
            if (gc instanceof VisitorCharacter) {
                for (GameCharacter gc2 : ((VisitorCharacter) gc).getSubtypes()) {
                    result.put(gc2.getBaseName(), new HashSet<>());
                }
            } else {
                result.put(gc.getBaseName(), new HashSet<>());
            }
        }
        return result;
    }


    private static Collection<PersonalGoal> createGeneralCoals() {
        HashSet<PersonalGoal> goals = new HashSet<>();
        goals.add(new CollectMoneyTask(MyRandom.nextInt(3)*300 + 900));
        goals.add(new ParasiteKiller(MyRandom.nextInt(3) + 3));
        goals.add(new FireManGoal(MyRandom.nextInt(3) + 3));
        goals.add(new HullBreachGoal(MyRandom.nextInt(2) + 2));
        goals.add(new PlaySlotsGoal(MyRandom.nextInt(2) + 3));
        goals.add(new AvengerGoal());
        goals.add(new CollectThreeChemicals());
        goals.add(new EatDifferentFoods(MyRandom.nextInt(2)+3));
        goals.add(new GoIntoSpaceGoal());
        goals.add(new SickOfYourselfGoal());
        goals.add(new LayerUponLayerGoal(MyRandom.nextInt(4)+5));
        goals.add(new PacifistGoal(MyRandom.nextInt(2)));
        goals.add(new PervertGoal());
        goals.add(new BeatUpTheClownGoal());
        goals.add(new DrinkAlcoholGoal(MyRandom.nextInt(2)+2));
        return goals;
    }
}
