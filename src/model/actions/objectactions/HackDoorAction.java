package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

public abstract class HackDoorAction extends Action {

    public HackDoorAction() {
        super("Hack Door", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "hacked a door";
    }

    @Override
    public boolean doesCommitThePlayer() {
        return true;
    }
}
