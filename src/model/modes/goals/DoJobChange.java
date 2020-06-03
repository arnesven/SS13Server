package model.modes.goals;

import model.actions.objectactions.ChangeJobAction;

public class DoJobChange extends DidAnActionGoal {

    public DoJobChange() {
        super(1, ChangeJobAction.class);
    }

    @Override
    protected String getNoun() {
        return "person's job";
    }

    @Override
    protected String getVerb() {
        return "Help change";
    }
}
