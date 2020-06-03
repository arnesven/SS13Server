package model.modes.goals;

import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.Action;

public class BuildNewRoomGoal extends DidAnActionGoal {
    public BuildNewRoomGoal() {
        super(1, BuildNewRoomAction.class);
    }

    @Override
    protected String getNoun() {
        return "new room.";
    }

    @Override
    protected String getVerb() {
        return "build at least";
    }
}
