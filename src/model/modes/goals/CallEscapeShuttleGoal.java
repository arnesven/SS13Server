package model.modes.goals;

import model.actions.objectactions.CallEscapeShuttleAction;

public class CallEscapeShuttleGoal extends DidAnActionGoal {
    public CallEscapeShuttleGoal() {
        super(1, CallEscapeShuttleAction.class);
    }

    @Override
    public String getText() {
        return "Call the Escape Shuttle sometime during the game";
    }

    @Override
    protected String getNoun() {
        return null;
    }

    @Override
    protected String getVerb() {
        return null;
    }
}
