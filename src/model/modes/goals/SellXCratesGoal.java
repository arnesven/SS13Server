package model.modes.goals;

import model.actions.objectactions.SellCrateAction;

public class SellXCratesGoal extends DidAnActionGoal {
    public SellXCratesGoal(int i) {
        super(i, SellCrateAction.class);
    }

    @Override
    protected String getNoun() {
        return "crates (using the Market Console).";
    }

    @Override
    protected String getVerb() {
        return "Sell at least ";
    }
}
