package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.objects.SoilPatch;

import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
public class MakeSoilAction extends Action {

    public MakeSoilAction() {
        super("Make Soil", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "made some soil appear";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.getPosition().addObject(new SoilPatch(performingClient.getPosition()));
        performingClient.addTolastTurnInfo("You made some soil appear!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
