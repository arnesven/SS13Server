package model.actions.general;

import model.Actor;
import model.GameData;

import java.util.List;

/**
 * Created by erini02 on 17/04/16.
 */
public abstract class HazardAction extends Action {
    public HazardAction() {
        super("Hazard Action", SensoryLevel.NO_SENSE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        doHazard(gameData);
    }

    public abstract void doHazard(GameData gameData);
}
