package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.general.GameItem;

public class HighFiveAction extends TargetingAction {
    private boolean successful = false;

    public HighFiveAction(Actor performer) {
        super("High Five", SensoryLevel.PHYSICAL_ACTIVITY, performer);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (target instanceof Player) {
            if (((Player) target).getNextAction() instanceof HighFiveAction) {
                Target otherPerson = ((HighFiveAction) ((Player) target).getNextAction()).getTarget();
                if (otherPerson == performer) {
                    performingClient.addTolastTurnInfo("SMACK! You and " + otherPerson.getName() + " high fived! Right on!");
                    successful = true;
                    return;
                }
            }
        }


        performingClient.addTolastTurnInfo(target.getName() + " left you hanging.");
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 != performer && target2 instanceof Actor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        if (successful) {
            return "high fived " + getTarget().getName();
        }

        return "wanted to high-five " + getTarget().getName() +", but was left hanging.";
    }


    	@Override
	public String getDescription(Actor whosAsking) {
        if (target == null) {
            return super.getDescription(whosAsking);
        }

		return performer.getPublicName() + " " + getVerb(whosAsking);
	}
}
