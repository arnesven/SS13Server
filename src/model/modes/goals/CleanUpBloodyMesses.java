package model.modes.goals;

import model.items.general.CleanUpBloodAction;

public class CleanUpBloodyMesses extends DidAnActionGoal {
    public CleanUpBloodyMesses(int i) {
        super(i, CleanUpBloodAction.class);
    }

    @Override
    protected String getNoun() {
        return "bloody Messes";
    }

    @Override
    protected String getVerb() {
        return "Clean up";
    }
}
