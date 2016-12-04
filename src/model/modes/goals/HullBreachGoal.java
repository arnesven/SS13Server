package model.modes.goals;

import model.actions.general.Action;
import model.actions.itemactions.SealHullBreachAction;

/**
 * Created by erini02 on 03/12/16.
 */
public class HullBreachGoal extends DidAnActionGoal {
    public HullBreachGoal() {
        super(2, SealHullBreachAction.class);
    }

    @Override
    protected String getNoun() {
        return "hull breaches";
    }

    @Override
    protected String getVerb() {
        return "Fix";
    }
}
