package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.characters.ChangelingCharacter;
import model.events.FollowMovementEvent;
import model.items.GameItem;
import model.items.weapons.Weapon;

public class HuntAction extends TargetingAction {

	// probably should be a map instead of static
	private static FollowMovementEvent followEvent;
	
	public HuntAction(Actor ap) {
		super("Start Hunting", SensoryLevel.NO_SENSE, ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		if (followEvent != null) {
			followEvent.setShouldBeRemoved(true);
		}
		followEvent = new FollowMovementEvent(performingClient.getPosition(), 
				performingClient, target, false);
		((Actor)target).addTolastTurnInfo(performingClient.getPublicName() + 
				" pounced at you!");
		target.beAttackedBy(performingClient, Weapon.CLAWS);
		gameData.addMovementEvent(followEvent);
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return ChangelingCharacter.isDetectable(target2);
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "";
	}

	

}
