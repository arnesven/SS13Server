package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.SensoryLevel;
import model.map.Room;
import model.npcs.NPC;

public class FollowMovementEvent extends Event {

	private Room shadowedInRoom;
	private Actor performingClient;
	private Target target;
	private boolean remove;

	public FollowMovementEvent(Room room, Actor performer, Target target, boolean remove) {
		this.shadowedInRoom = room;
		this.performingClient = performer;
		this.target = target;
		this.remove = remove;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

	@Override
	public void apply(GameData gameData) {
		if (shadowedInRoom == performingClient.getPosition()) {
			if (performingClient instanceof Player) {
				Player pl = (Player)performingClient;
				
				pl.moveIntoRoom(target.getPosition());
				performingClient.addTolastTurnInfo("You followed " + target.getName() + " to " +target.getPosition().getName() + ".");
				shadowedInRoom = target.getPosition();
			} else {
				NPC npc = ((NPC)performingClient);
				npc.moveIntoRoom(target.getPosition());
			}
		} else {
			performingClient.addTolastTurnInfo("You stopped shadowing " + target.getName() + ".");

		}
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return remove || target.isDead();
	}
	
	public void setShouldBeRemoved(boolean b) {
		remove = b;
	}



}
