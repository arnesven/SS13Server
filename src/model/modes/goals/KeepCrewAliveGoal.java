package model.modes.goals;

import model.Actor;
import model.GameData;

public class KeepCrewAliveGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Keep your crew alive! More than half of the crew must be living at the end of the game for this goal to be completed!";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        int crewCount = 0;
        int aliveCrewCount = 0;
        for (Actor a : gameData.getActors()) {
            if (a.isCrew()) {
                crewCount++;
                if (!a.isDead()) {
                    aliveCrewCount++;
                }
            }
        }
        double ratio = ((double)crewCount)/((double)aliveCrewCount);

        return ratio > 0.5;
    }
}
