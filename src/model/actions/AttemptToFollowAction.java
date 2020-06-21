package model.actions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.FollowMovementEvent;
import util.Logger;

import java.util.List;

public class AttemptToFollowAction extends Action {
    private final Actor target;

    public AttemptToFollowAction(Actor belongingTo) {
        super("Attempt to Follow", SensoryLevel.PHYSICAL_ACTIVITY);
        this.target = belongingTo;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "moved";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        FollowMovementEvent followEvent = new FollowMovementEvent(performingClient.getPosition(), performingClient, target.getAsTarget());
        gameData.addMovementEvent(followEvent);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        Logger.log(performingClient.getBaseName() + " is attempting to follow " + target.getPublicName());
    }
}
