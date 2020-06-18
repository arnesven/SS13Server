package model.actions.itemactions;

import model.actions.QuickAction;
import model.characters.general.GameCharacter;
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

import java.util.List;

public class HealWithMedKitAction extends TargetingAction implements QuickAction {

	private MedKit objectRef;
	private static final double HEAL_AMOUNT = 1.5;

	public HealWithMedKitAction(Actor ap, MedKit objectRef) {
		super("Heal", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		this.objectRef = objectRef;
		this.performer = ap;
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		if (target.isDead()) {
			performingClient.addTolastTurnInfo(target.getName() + " is already dead!");
			return;
		}
		
		if (! target.hasSpecificReaction(objectRef)) {
			if (!performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DoctorCharacter) ||
					MyRandom.nextDouble() < DoctorCharacter.MEDKIT_USAGE_CHANCE) {
				if ( performingClient.getItems().contains(objectRef)) {
                	objectRef.decrementUses();
                	if (objectRef.isEmpty()) {
						performingClient.getItems().remove(objectRef);
						performingClient.addTolastTurnInfo("The MedKit was used up.");
					}
			    } else if (performingClient.getPosition().getItems().contains(objectRef)) {
                	objectRef.decrementUses();
					if (objectRef.isEmpty()) {
						performingClient.getPosition().getItems().remove(objectRef);
						performingClient.addTolastTurnInfo("The MedKit was used up.");
					}
                } else {
                    performingClient.addTolastTurnInfo("What? The MedKit was missing! Your action failed!");
                    return;
                }
			}

            target.addToHealth(HEAL_AMOUNT);
			objectRef.makeHoldInHand(performingClient);

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
	protected boolean requiresProximityToTarget() {
		return true;
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
