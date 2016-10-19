package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class BarkingAction extends Action {

    public BarkingAction() {
        super("Bark", SensoryLevel.SPEECH);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "barked";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You barked.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
