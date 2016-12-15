package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.WatchAction;
import model.events.FollowMovementEvent;
import model.items.general.GameItem;
import model.map.rooms.Room;

public class ShadowAction extends WatchAction {

	private Room shadowedInRoom;

	public ShadowAction(Actor actor) {
		super(actor);
		super.setName("Shadow");
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Shadowed";
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			final Actor performingClient, final Target target, 
			GameItem item) {
		super.applyTargetingAction(gameData, performingClient, target, item);
		performingClient.addTolastTurnInfo("You are shadowing " + target.getName());
		this.shadowedInRoom = performingClient.getPosition();
				
		gameData.addMovementEvent(new FollowMovementEvent(shadowedInRoom, performingClient, target, true));
	}
	
}
