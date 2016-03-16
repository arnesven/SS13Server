package model.actions.itemactions;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.characters.GameCharacter;
import model.characters.crew.DoctorCharacter;
import model.characters.decorators.InstanceChecker;
import model.items.GameItem;
import model.items.MedKit;

public class HealWithMedKitAction extends TargetingAction {

	private MedKit objectRef;
	private static final double HEAL_AMOUNT = 1.0;

	public HealWithMedKitAction(Actor ap, MedKit objectRef) {
		super("Heal", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		this.objectRef = objectRef;
		this.performer = ap;
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		if (target.isDead()) {
			performingClient.addTolastTurnInfo(target.getName() + "Is already dead!"); 
			return;
		}
		
		if (! target.hasSpecificReaction(objectRef)) {
			target.addToHealth(HEAL_AMOUNT);
			performingClient.getItems().remove(objectRef);
			if (target == performingClient) {
				performingClient.addTolastTurnInfo("You " + getVerb(performingClient) + " yourself with the " + objectRef.getPublicName(performingClient) + ".");
			} else {
				performingClient.addTolastTurnInfo("You " + getVerb(performingClient) + " " + target.getName() + " with the " + objectRef.getPublicName(performingClient) + ".");
				if (target instanceof Player) {
					((Player)target).addTolastTurnInfo(performingClient.getPublicName() + " " + getVerb((Player)target) + 
												       " you with a " + objectRef.getPublicName(((Player)target)) + ".");
				}
				
			}
		}

	}
	
	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2.isHealable() && !target2.isDead();
	}
	
	@Override
	protected void addMoreTargets(Actor ap) {
		if (ap.getAsTarget().isHealable() && 
				ap.getAsTarget().getHealth() < ap.getAsTarget().getMaxHealth()) {
			this.addTarget(ap.getAsTarget());
		}
	}

}
