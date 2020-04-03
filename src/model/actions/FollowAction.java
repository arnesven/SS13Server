package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.FollowingDecorator;
import model.characters.general.GameCharacter;
import model.events.FollowMovementEvent;
import model.items.general.GameItem;

public class FollowAction extends TargetingAction {

    public FollowAction(Actor ap) {
        super("Follow", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {

        removeAllFollowings(performingClient);


        FollowMovementEvent followEvent = new FollowMovementEvent(performingClient.getPosition(), performer, target, false);
        performingClient.setCharacter(new FollowingDecorator(performingClient.getCharacter(), target, followEvent));
        gameData.addMovementEvent(followEvent);
    }

    private void removeAllFollowings(Actor performingClient) {
        for (FollowingDecorator fd = StopFollowingAction.getFollowingDecorator(performer); fd != null; fd = StopFollowingAction.getFollowingDecorator(performer)) {
            fd.getFollowEvent().setShouldBeRemoved(true);
            performingClient.removeInstance((GameCharacter gc) -> gc instanceof FollowingDecorator);
        }

    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return !target2.isDead() && !isFollowing(performer, target2);
    }

    private boolean isFollowing(Actor performer, Target target2) {
        FollowingDecorator fd = StopFollowingAction.getFollowingDecorator(performer);
        if (fd == null) {
            return false;
        }

        return fd.getWhosBeingFallowed().getAsTarget() == target2;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        if (getPerformer() == whosAsking) {
            return "are following";
        }
        return "is following";
    }

}
