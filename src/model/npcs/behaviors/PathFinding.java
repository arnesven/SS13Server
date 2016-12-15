package model.npcs.behaviors;

import java.util.HashSet;
import java.util.Set;

import model.Actor;
import model.map.GameMap;
import model.map.rooms.Room;
import util.Logger;

public class PathFinding {
	
	public static Room findCloserRoom(Actor fromWhom, Room r) {
		Logger.log(" "  + fromWhom.getBaseName() + ": Finding closest room to " + r.getName());
		Set<Room> rooms = new HashSet<>();
		Logger.log(" "  + fromWhom.getBaseName() + ": my move steps are " + fromWhom.getCharacter().getMovementSteps());
		recursivelyGetAccissibleRooms(fromWhom.getPosition(), rooms, fromWhom.getCharacter().getMovementSteps());

		Room best = fromWhom.getPosition();
		int least = 10000;
		for (Room r2 : rooms) {
			int dist = GameMap.shortestDistance(r2, r);
			Logger.log(" "  + fromWhom.getBaseName() + ": a room I can go to: " + r2.getName());
			Logger.log(" "  + fromWhom.getBaseName() + ": it's " + dist + " rooms away");

			if (dist < least) {
				least = dist;
				best = r2;
			}
		}
		Logger.log(" "  + fromWhom.getBaseName() + ": Best room to go to is " + best.getName());

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
