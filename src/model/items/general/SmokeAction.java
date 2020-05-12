package model.items.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

public class SmokeAction extends Action {
    public SmokeAction() {
        super("Smoke", new SensoryLevel(SensoryLevel.VisualLevel.VISIBLE_IF_CLOSE, SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.WHIFF));
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "smoked";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("*Puff puff - cough cough* Smoking is bad for you...");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
