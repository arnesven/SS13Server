package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.items.GameItem;
import model.objects.BreakableObject;
import model.objects.Repairable;

public class RepairAction extends TargetingAction {

	public RepairAction(Actor ap) {
		super("Repair", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		((BreakableObject)target).addToHealth(1.0);
		performingClient.addTolastTurnInfo("You repaired " + target.getName());
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2 instanceof Repairable && 
				( ((Repairable)target2).isDamaged() || ((Repairable)target2).isBroken() );
		
	}
}