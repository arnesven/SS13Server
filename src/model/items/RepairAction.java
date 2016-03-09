package model.items;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.actions.Target;
import model.actions.TargetingAction;
import model.objects.BreakableObject;
import model.objects.Repairable;

class RepairAction extends TargetingAction {

	public RepairAction(String name, SensoryLevel s, Actor ap) {
		super(name, s, ap);
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