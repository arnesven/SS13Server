package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.GetsNoActionsDecorator;
import model.characters.decorators.HandCuffedDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.HandCuffs;
import model.items.NoSuchThingException;
import model.items.general.GameItem;

/**
 * Created by erini02 on 21/11/16.
 */
public class PutHandCuffsOnAction extends TargetingAction {
    public PutHandCuffsOnAction(Actor ap) {
        super("Put Handcuffs On", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        this.target = target;
        HandCuffs hc;
        try {
            hc = GameItem.getItemFromActor(performingClient, new HandCuffs());
        } catch (NoSuchThingException e) {
             performingClient.addTolastTurnInfo("What, the handcuffs are missing? " + failed(gameData, performingClient));
            return;
        }

        if (GameCharacter.isAttacking((Actor)target, performingClient)) {
            performingClient.addTolastTurnInfo(((Actor) target).getPublicName() + " resisted you!");
            return;
        }


        performingClient.addTolastTurnInfo("You put handcuffs on " + target.getName());
        performingClient.getItems().remove(hc);

        ((Actor) target).addTolastTurnInfo(performingClient.getPublicName() + " put handcuffs on you!");
        ((Actor) target).setCharacter(new HandCuffedDecorator(((Actor)target).getCharacter()));

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "put handcuffs on " + target.getName();
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return super.isViableForThisAction(target2) && target2 instanceof Actor &&
                ((Actor) target2).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter);
    }
}
