package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.general.ChangelingCharacter;
import model.events.FollowMovementEvent;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

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
        if (performingClient.getPosition() != target.getPosition()) {
            performingClient.moveIntoRoom(target.getPosition());
        }
		followEvent = new FollowMovementEvent(performingClient.getPosition(), 
				performingClient, target, false);
		((Actor)target).addTolastTurnInfo(performingClient.getPublicName() + 
				" pounced at you!");
		target.beAttackedBy(performingClient, Weapon.CLAWS, gameData);
		gameData.addMovementEvent(followEvent);
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return ChangelingCharacter.isDetectable(target2);
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		applyTargetingAction(gameData, performingClient, target, item);
	}
	
	@Override
	protected void addMoreTargets(Actor ap) {
		for (Room r : ap.getPosition().getNeighborList()) {
			for (Actor a : r.getActors()) {
				if (ChangelingCharacter.isDetectable(a.getAsTarget())) {
					addTarget(a.getAsTarget());
				}
			}
		}
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "";
	}

	

}
