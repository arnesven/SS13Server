package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.SensoryLevel;
import model.map.GameMap;
import model.map.rooms.Room;
import model.modes.GameMode;
import model.npcs.NPC;
import model.npcs.behaviors.PathFinding;
import util.Logger;

public class FollowMovementEvent extends Event {

	private Room shadowedInRoom;
	private Actor performingClient;
	private Target target;
	private boolean remove;

	public FollowMovementEvent(Room room, Actor performer, Target target) {
		this.shadowedInRoom = room;
		this.performingClient = performer;
		this.target = target;
		this.remove = false;
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
		if (remove) {
			return;
		}
		if (shadowedInRoom == target.getPosition()) {
			Logger.log("No need to follow, same room still...");
			return;
		}

		if (shadowedInRoom == performingClient.getPosition()) {
            if (GameMap.shortestDistance(target.getPosition(), performingClient.getPosition()) <=
                    performingClient.getCharacter().getMovementSteps()) {

                if (performingClient instanceof Player) {
                    Player pl = (Player) performingClient;

                    pl.moveIntoRoom(target.getPosition());
                    performingClient.addTolastTurnInfo("You followed " + target.getName() + " to " + target.getPosition().getName() + ".");
                    shadowedInRoom = target.getPosition();
                } else {
                    NPC npc = ((NPC) performingClient);
                    npc.moveIntoRoom(target.getPosition());
                }
            } else {
            	Room r = PathFinding.findCloserRoom(performingClient, target.getPosition());
				Player pl = (Player) performingClient;
				pl.moveIntoRoom(r);
				performingClient.addTolastTurnInfo("You tried to follow your target, but you must have lost it.");
            }
		} else {
			performingClient.addTolastTurnInfo("You stopped following " + target.getName() + ".");
			remove = true;
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
