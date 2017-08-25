package model.modes.goals;

import model.actions.itemactions.HealWithMedKitAction;

/**
 * Created by erini02 on 25/08/17.
 */
public class HealXTimes extends DidAnActionGoal {
    public HealXTimes(int i) {
        super(i, HealWithMedKitAction.class);
    }

    @Override
    protected String getNoun() {
        return "living beings";
    }

    @Override
    protected String getVerb() {
        return "heal";
    }
}
