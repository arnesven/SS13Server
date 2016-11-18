package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.general.ElectronicParts;
import model.items.general.GameItem;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import util.MyRandom;

/**
 * Created by erini02 on 17/11/16.
 */
public class DismantleAction extends TargetingAction {
    private Target removedTarget;

    public DismantleAction(Actor ap) {
        super("Dismantle", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        target.getPosition().removeObject((GameObject)target);
        this.removedTarget = target;
        for (int i = MyRandom.nextInt(1)+1; i >= 0; i--) {
            performingClient.addItem(new ElectronicParts(), target);
        }
        performingClient.addTolastTurnInfo("You salvaged some parts from the " +
                ((GameObject) target).getPublicName(performingClient) + ".");
    }


    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof BreakableObject && ((BreakableObject) target2).canBeDismantled() &&
                target2.getHealth() == 0.0;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "dismantled the " + removedTarget.getName();
    }
}
