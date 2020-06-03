package model.modes.goals;

import model.actions.objectactions.SetSolarPanelRotationAction;

public class AlignSolarArraysGoal extends DidAnActionGoal {
    public AlignSolarArraysGoal(int i) {
        super(i, SetSolarPanelRotationAction.class);
    }

    @Override
    protected String getNoun() {
        return "solar arrays (they do not have to be perfectly aligned).";
    }

    @Override
    protected String getVerb() {
        return "Set the rotation of";
    }
}
