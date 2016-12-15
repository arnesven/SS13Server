package model.npcs.behaviors;

import model.map.rooms.Room;

public class GuardBehavior extends MoveTowardsBehavior {
	public GuardBehavior(Room target) {
		super(target, new StayBehavior(), new AttackBaddiesBehavior());
	}
}
