package model.modes.goals;

import model.Actor;
import model.actions.itemactions.PutOutFireAction;

/**
 * Created by erini02 on 03/12/16.
 */
public class FireManGoal extends DidAnActionGoal {
    public FireManGoal() {
        super(3, PutOutFireAction.class);
    }

    @Override
    protected String getNoun() {
        return "fires";
    }

    @Override
    protected String getVerb() {
        return "Put out";
    }
}
