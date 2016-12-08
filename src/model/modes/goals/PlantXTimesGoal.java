package model.modes.goals;

import model.actions.objectactions.PlantAction;

/**
 * Created by erini02 on 08/12/16.
 */
public class PlantXTimesGoal extends DidAnActionGoal {
    public PlantXTimesGoal(int i) {
        super(i, PlantAction.class);
    }

    @Override
    protected String getNoun() {
        return "seeds";
    }

    @Override
    protected String getVerb() {
        return "plant";
    }
}
