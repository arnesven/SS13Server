package model.npcs.behaviors;

import java.util.HashSet;
import java.util.Set;

import model.map.GameMap;
import model.map.Room;
import model.npcs.NPC;

public class MoveTowardsBehavior implements MovementBehavior {

	private Room target;
	private MovementBehavior nextMovement;
	private ActionBehavior nextAction;
	
	/**
	 * The NPC move towards a specified room and changes its behavior once it has reached its target
	 * @param target : the room the NPC should move towards
	 * @param nextMovement : what movement behavior the NPC will adopt as soon as it has reached the target
	 * @param nextAction : what action behavior the NPC will adopt as soon as it has reached the target
	 */
	MoveTowardsBehavior(Room target, MovementBehavior nextMovement, ActionBehavior nextAction) {
		this.target = target;
		this.nextMovement = nextMovement;
		this.nextAction = nextAction;
	}
	
	@Override
	public void move(NPC npc) {
		if (npc.getPosition() == target) {
			npc.setMoveBehavior(nextMovement);
			npc.setActionBehavior(nextAction);
			return;
		}
		
		Room closerRoom = findCloserRoom(npc, target);
		npc.moveIntoRoom(closerRoom);
	}

	// TODO these are basically copy pasted from FollowCriminalBehavior, abstract it out
	
	private Room findCloserRoom(NPC npc, Room r) {
		Set<Room> rooms = new HashSet<>();
		recursivelyGetAccissibleRooms(npc.getPosition(), rooms, npc.getCharacter().getMovementSteps());

		Room best = null;
		int least = 10000;
		for (Room r2 : rooms) {
			int dist = GameMap.shortestDistance(r2, r);

			if (dist < least) {
				least = dist;
				best = r2;
			}
		}
		return best;
	}

	private void recursivelyGetAccissibleRooms(Room position, Set<Room> rooms, int steps) {

		rooms.add(position);
		if (steps == 0) {
			return;
		}

		for (Room n : position.getNeighborList()) {
			if (!rooms.contains(n)) {
				recursivelyGetAccissibleRooms(n, rooms, steps-1);
			}
		}
	}
}
