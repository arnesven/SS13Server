package model.actions.itemactions;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.crew.DoctorCharacter;
import model.items.general.GameItem;
import model.items.general.MedKit;

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

			
			if ((!(performingClient.getCharacter() instanceof DoctorCharacter)) ||
					MyRandom.nextDouble() < 0.5) {
                if ( performingClient.getItems().contains(objectRef)) {
                    performingClient.getItems().remove(objectRef);
                } else if (performingClient.getPosition().getItems().contains(objectRef)) {
                    performingClient.getPosition().getItems().remove(objectRef);
                } else {
                    performingClient.addTolastTurnInfo("What? The MedKit was missing! Your action failed!");
                }
			} else {
				performingClient.addTolastTurnInfo("The MedKit wasn't used up.");
			}
            target.addToHealth(HEAL_AMOUNT);
			
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

	@Override
	protected String getVerb(Actor whosAsking) {
		return "healed";
	}

}
