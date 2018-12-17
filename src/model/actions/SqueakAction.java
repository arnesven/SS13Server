package model.actions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

public class SqueakAction extends Action {

    public SqueakAction() {
        super("Squeak", SensoryLevel.SPEECH);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "squeaked";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You squeaked.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
