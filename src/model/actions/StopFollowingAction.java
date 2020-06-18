package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.FollowingDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import util.Logger;

import java.util.List;

public class StopFollowingAction extends TargetingAction implements QuickAction {

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

    @Override
    protected boolean requiresProximityToTarget() {
        return false;
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

    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        return List.of(performer);
    }
}
