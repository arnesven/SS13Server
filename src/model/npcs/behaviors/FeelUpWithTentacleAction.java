package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;

import java.util.List;

public class FeelUpWithTentacleAction extends Action {

    private final Actor target;

    public FeelUpWithTentacleAction(Actor target) {
        super("Feel Up With Tentacle", SensoryLevel.PHYSICAL_ACTIVITY);
        this.target = target;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "felt " + target.getPublicName(whosAsking) + " up with tentacle";
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (target.getPosition() != performingClient.getPosition()) {
            performingClient.addTolastTurnInfo("What, " + target.getPublicName(performingClient) +
                    " wasn't there?" + failed(gameData, performingClient));
            return;
        }

        target.addTolastTurnInfo(performingClient.getPublicName(target) + " felt you upp with a tentacle - gross!");
        performingClient.addTolastTurnInfo("You felt " + target.getPublicName(performingClient) + " up with a tentacle.");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

}
