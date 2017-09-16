package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.objects.general.JungleCampFire;

import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
public class StartCampFire extends Action {

    public StartCampFire() {
        super("Start Camp Fire", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "started a camp fire";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You started a camp fire.");
        performingClient.getPosition().addObject(new JungleCampFire(performingClient.getPosition(), gameData));
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
