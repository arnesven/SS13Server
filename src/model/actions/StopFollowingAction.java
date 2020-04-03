package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.FollowingDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import util.Logger;

public class StopFollowingAction extends TargetingAction {

    public StopFollowingAction(Actor ap) {
        super("Stop Following", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        FollowingDecorator fd = getFollowingDecorator();
        fd.getFollowEvent().setShouldBeRemoved(true);
        performingClient.addTolastTurnInfo("You stopped following " + target.getName());
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        FollowingDecorator fd = getFollowingDecorator();
        if (fd == null) {
            return false;
        }
        return fd.getWhosBeingFallowed().getAsTarget() == target2;
    }

    private FollowingDecorator getFollowingDecorator() {
       return getFollowingDecorator(performer);
    }

    public static FollowingDecorator getFollowingDecorator(Actor performer) {
        GameCharacter gc = performer.getCharacter();
        while (gc instanceof CharacterDecorator) {
            if (gc instanceof FollowingDecorator) {
                FollowingDecorator fd = (FollowingDecorator)gc;
                return fd;
            }
            gc = ((CharacterDecorator) gc).getInner();
        }
        return null;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stopped following";
    }
}
