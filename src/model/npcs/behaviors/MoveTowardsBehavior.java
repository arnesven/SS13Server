package model.npcs.behaviors;

import model.map.rooms.Room;
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
	public MoveTowardsBehavior(Room target, MovementBehavior nextMovement, ActionBehavior nextAction) {
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
		
		Room closerRoom = PathFinding.findCloserRoom(npc, target);
		npc.moveIntoRoom(closerRoom);
	}
}
