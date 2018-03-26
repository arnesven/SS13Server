package model.modes.goals;

import model.actions.general.Action;
import model.actions.itemactions.BuildRobotAction;

public class BuildRobotsAction extends DidAnActionGoal {
    public BuildRobotsAction() {
        super(2, BuildRobotAction.class);
    }

    @Override
    protected String getNoun() {
        return "new robots";
    }

    @Override
    protected String getVerb() {
        return "Build";
    }
}
