package model.modes.goals;

import model.actions.characteractions.MarriageAction;

public class MarryXPeople extends DidAnActionGoal {
    public MarryXPeople(int i) {
        super(i, MarriageAction.class);
    }

    @Override
    protected String getNoun() {
        return "marriages";
    }

    @Override
    protected String getVerb() {
        return "Perform";
    }
}
