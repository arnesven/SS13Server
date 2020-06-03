package model.modes.goals;

import model.actions.objectactions.JumpStationAction;

public class JumpTheStationGoal extends DidAnActionGoal {
    public JumpTheStationGoal() {
        super(1, JumpStationAction.class);
    }

    @Override
    public String getText() {
        return "FTL-Jump the station once during this game.";
    }

    @Override
    protected String getNoun() {
        return "the station";
    }

    @Override
    protected String getVerb() {
        return "FTL-Jump";
    }
}
