package model.modes.goals;

import model.actions.general.Action;
import model.actions.objectactions.SlotMachineAction;

/**
 * Created by erini02 on 03/12/16.
 */
public class PlaySlotsGoal extends DidAnActionGoal {
    public PlaySlotsGoal(int i) {
        super(i, SlotMachineAction.class);
    }

    @Override
    protected String getNoun() {
        return "times on the slotmachine";
    }

    @Override
    protected String getVerb() {
        return "Play";
    }
}
