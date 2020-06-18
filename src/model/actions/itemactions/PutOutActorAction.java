package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.QuickAction;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.List;


/**
 * Created by erini02 on 10/09/17.
 */
public class PutOutActorAction extends TargetingAction implements QuickAction {

    public PutOutActorAction(Actor performer) {
        super("Put Out", SensoryLevel.PHYSICAL_ACTIVITY, performer);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Put out " + target.getName();
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {

        Actor targetAsActor = (Actor) target;
        if (targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
            targetAsActor.removeInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
            performingClient.addTolastTurnInfo("You put " + ((Actor) target).getPublicName() + " out.");
            ((Actor) target).addTolastTurnInfo("You were put out by " + performingClient.getPublicName());
        }

    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        if (target2 instanceof Actor) {
            Actor targetAsActor = (Actor) target2;
            return targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
        }
        return false;
    }

    @Override
    protected boolean requiresProximityToTarget() {
        return true;
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
        return performer.getPosition().getClients();
    }
}
