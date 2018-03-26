package model.modes.goals;

import model.actions.itemactions.InjectionAction;

public class InjectXTimes extends DidAnActionGoal {
    public InjectXTimes(int i) {
        super(i, InjectionAction.class);
    }

    @Override
    protected String getNoun() {
        return "targets with a syringe.";
    }

    @Override
    protected String getVerb() {
        return "Inject";
    }
}
