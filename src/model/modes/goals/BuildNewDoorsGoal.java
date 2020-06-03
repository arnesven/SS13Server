package model.modes.goals;

import model.actions.itemactions.BuildDoorAction;

public class BuildNewDoorsGoal extends DidAnActionGoal {
    public BuildNewDoorsGoal(int i) {
        super(i, BuildDoorAction.class);
    }

    @Override
    protected String getNoun() {
        return "new doors";
    }

    @Override
    protected String getVerb() {
        return "Build at least";
    }
}
