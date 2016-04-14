package model.npcs.behaviors;

import java.util.HashSet;
import java.util.Set;

import model.map.GameMap;
import model.map.Room;
import model.npcs.NPC;

public abstract class PathFinding implements MovementBehavior {
	
	public Room findCloserRoom(NPC npc, Room r) {
		System.out.println(" "  + npc.getBaseName() + ": Finding closest room to " + r.getName());
		Set<Room> rooms = new HashSet<>();
		System.out.println(" "  + npc.getBaseName() + ": my move steps are " + npc.getCharacter().getMovementSteps());
		recursivelyGetAccissibleRooms(npc.getPosition(), rooms, npc.getCharacter().getMovementSteps());

		Room best = npc.getPosition();
		int least = 10000;
		for (Room r2 : rooms) {
			int dist = GameMap.shortestDistance(r2, r);
			System.out.println(" "  + npc.getBaseName() + ": a room I can go to: " + r2.getName());
			System.out.println(" "  + npc.getBaseName() + ": it's " + dist + " rooms away");

			if (dist < least) {
				least = dist;
				best = r2;
			}
		}
		System.out.println(" "  + npc.getBaseName() + ": Best room to go to is " + best.getName());

		return best;
	}

	public void recursivelyGetAccissibleRooms(Room position, Set<Room> rooms, int steps) {

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
