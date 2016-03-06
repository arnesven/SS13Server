package model.actions;

import model.Actor;
import model.Player;
import model.GameData;
import model.items.GameItem;
import model.items.MedKit;

public class HealWithMedKitAction extends TargetingAction {

	private MedKit objectRef;
	private static final double HEAL_AMOUNT = 1.0;

	public HealWithMedKitAction(Actor ap, MedKit objectRef) {
		super("Heal", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		this.objectRef = objectRef;
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		if (! target.hasSpecificReaction(objectRef)) {
			target.addToHealth(HEAL_AMOUNT);
			performingClient.getItems().remove(objectRef);
			if (target == performingClient) {
				performingClient.addTolastTurnInfo("You " + getVerb() + " yourself with the " + objectRef.getName() + ".");
			} else {
				performingClient.addTolastTurnInfo("You " + getVerb() + " " + target.getName() + " with the " + objectRef.getName() + ".");
				if (target instanceof Player) {
					((Player)target).addTolastTurnInfo(performingClient.getPublicName() + " " + getVerb() + 
												       " you with a " + objectRef.getName() + ".");
				}
				
			}
		}

	}
	
	@Override
	protected boolean isViableForThisAction(Target target2) {
		return target2.isHuman() && !target2.isDead();
	}
	
	@Override
	protected void addMoreTargets(Actor ap) {
		if (ap.getAsTarget().isHuman() && 
				ap.getAsTarget().getHealth() < ap.getAsTarget().getMaxHealth()) {
			this.addTarget(ap.getAsTarget());
		}
	}

}
