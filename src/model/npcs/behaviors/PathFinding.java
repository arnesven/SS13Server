package model.npcs.behaviors;

import java.util.HashSet;
import java.util.Set;

import model.Actor;
import model.map.GameMap;
import model.map.Room;

public class PathFinding {
	
	public static Room findCloserRoom(Actor fromWhom, Room r) {
		System.out.println(" "  + fromWhom.getBaseName() + ": Finding closest room to " + r.getName());
		Set<Room> rooms = new HashSet<>();
		System.out.println(" "  + fromWhom.getBaseName() + ": my move steps are " + fromWhom.getCharacter().getMovementSteps());
		recursivelyGetAccissibleRooms(fromWhom.getPosition(), rooms, fromWhom.getCharacter().getMovementSteps());

		Room best = fromWhom.getPosition();
		int least = 10000;
		for (Room r2 : rooms) {
			int dist = GameMap.shortestDistance(r2, r);
			System.out.println(" "  + fromWhom.getBaseName() + ": a room I can go to: " + r2.getName());
			System.out.println(" "  + fromWhom.getBaseName() + ": it's " + dist + " rooms away");

			if (dist < least) {
				least = dist;
				best = r2;
			}
		}
		System.out.println(" "  + fromWhom.getBaseName() + ": Best room to go to is " + best.getName());

		return best;
	}

	public static void recursivelyGetAccissibleRooms(Room position, Set<Room> rooms, int steps) {

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
