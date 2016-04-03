package model.npcs;

import model.Actor;
import model.GameData;
import model.Target;
import model.events.FollowMovementEvent;

public class FollowMeBehavior implements MovementBehavior {

	private Target target;
	private GameData gameData;

	public FollowMeBehavior(Actor performer, GameData gameData) {
		this.target = performer.getAsTarget();
		this.gameData = gameData;
	}
	

	@Override
	public void move(NPC npc) {
		gameData.addMovementEvent(new FollowMovementEvent(npc.getPosition(), npc, this.target));		
	}



}
