package model.modes.goals;

import model.GameData;
import model.items.NoSuchThingException;
import model.objects.monolith.MonolithExperimentRig;

public class FinishMonolithExperimentGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Finish the Monolith Experiment by sending in the report (you conclusion does not need to be correct.)";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            MonolithExperimentRig rig = gameData.findObjectOfType(MonolithExperimentRig.class);
            if (rig.wasReportSent()) {
                return true;
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
