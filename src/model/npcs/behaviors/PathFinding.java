package model.npcs.behaviors;

import java.util.*;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.doors.Door;
import model.map.rooms.Room;
import util.Logger;

public class PathFinding {

	private static final int MAX_STEPS = 8;
	private static final Integer UNACCEPTABLE_DISTANCE = 1000000;

//	public static Room findCloserRoom(Actor fromWhom, Room r) {
//		Logger.log(" "  + fromWhom.getBaseName() + ": Finding closest room to " + r.getName());
//		Set<Room> rooms = new HashSet<>();
//		Logger.log(" "  + fromWhom.getBaseName() + ": my move steps are " + fromWhom.getCharacter().getMovementSteps());
//		recursivelyGetAccissibleRooms(fromWhom.getPosition(), rooms, fromWhom.getCharacter().getMovementSteps());
//
//		Room best = fromWhom.getPosition();
//		int least = 10000;
//		for (Room r2 : rooms) {
//			int dist = GameMap.shortestDistance(r2, r);
//			Logger.log(" "  + fromWhom.getBaseName() + ": a room I can go to: " + r2.getName());
//			Logger.log(" "  + fromWhom.getBaseName() + ": it's " + dist + " rooms away");
//
//			if (dist < least) {
//				least = dist;
//				best = r2;
//			}
//		}
//		Logger.log(" "  + fromWhom.getBaseName() + ": Best room to go to is " + best.getName());
//
//		return best;
//	}
//
//	public static void recursivelyGetAccissibleRooms(Room position, Set<Room> rooms, int steps) {
//
//		rooms.add(position);
//		if (steps == 0) {
//			return;
//		}
//
//		for (Room n : position.getNeighborList()) {
//			if (!rooms.contains(n)) {
//				recursivelyGetAccissibleRooms(n, rooms, steps-1);
//			}
//		}
//	}

	public static Room findCloserRoom(Actor forWhom, Room targetRoom) {
		Map<Room, Integer> distanceMap = getDistanceMap(List.of(forWhom.getPosition()), targetRoom);

		if (distanceMap.get(forWhom.getPosition()) == UNACCEPTABLE_DISTANCE) {
			Logger.log(forWhom.getBaseName() + " can't get to " + targetRoom.getName() + ", staying in position");
			return forWhom.getPosition();
		}

		if (distanceMap.get(forWhom.getPosition()) <= forWhom.getCharacter().getMovementSteps()) {
			Logger.log(forWhom.getBaseName() + " is within range of target room, moves directly");
			return targetRoom;
		}

		int newDistance = distanceMap.get(forWhom.getPosition()) - forWhom.getCharacter().getMovementSteps();
		for (Room r : distanceMap.keySet()) {
			if (distanceMap.get(r) == newDistance) {
				Logger.log("Found a middlepoint which is " + forWhom.getCharacter().getMovementSteps() + " closer to target: " + r.getName());
				return r;
			}
		}
		throw new IllegalStateException("Did not find middlepoint room! Can this really happen???");
	}

	public static Map<Room, Integer> getDistanceMap(List<Room> destinations, Room pointOfdeparture) {
		List<Room> targetsRemaining = new ArrayList<>();
		targetsRemaining.addAll(destinations);
		Map<Room, Integer> result = new HashMap<>();
		
		result.put(pointOfdeparture, 0);
		targetsRemaining.remove(pointOfdeparture);
		
		for (int i = MAX_STEPS; i > 0; --i) {
			List<Room> roomsToTraverse = new ArrayList<>();
			roomsToTraverse.addAll(result.keySet());
			for (Room r : roomsToTraverse) {
				for (Room neigh : r.getNeighborList()) {
					if (!result.containsKey(neigh)) {
						result.put(neigh, result.get(r) + 1);
						targetsRemaining.remove(neigh);
					}
				}
			}
			if (targetsRemaining.isEmpty()) {
				break;
			}
		}

		while (!targetsRemaining.isEmpty()) {
			result.put(targetsRemaining.remove(0), UNACCEPTABLE_DISTANCE);
		}
		
		return result;
	}

	public static Room findCloserRoomThroughDoors(GameData gameData, Player cl, Room target) {
		List<Room> candidates = new ArrayList<>();
		Logger.log("...Looking for suitable candidates to go to");


		for (Door d : target.getDoors()) {
			try {
				Room to = gameData.getRoomForId(d.getToId());
				Room from = gameData.getRoomForId(d.getFromId());
				if (to != target) {
					candidates.add(to);
					Logger.log(to.getName() + " is a candidate");
				}
				if (from != target) {
					candidates.add(from);
					Logger.log(from.getName() + " is a candidate");
				}
			} catch (NoSuchThingException e) {
				e.printStackTrace();
			}
		}


		Set<Room> moveToTargets = cl.findMoveToAblePositions(gameData);
		for (Room r : moveToTargets) {
			for (Door d : r.getDoors()) {
				try {
					Room to = gameData.getRoomForId(d.getToId());
					Room from = gameData.getRoomForId(d.getFromId());
					if (to == target) {
						candidates.add(from);
						Logger.log(from.getName() + " is a candidate");
					}
					if (from == target) {
						candidates.add(to);
						Logger.log(to.getName() + " is a candidate");
					}
				} catch (NoSuchThingException e) {
					e.printStackTrace();
				}
			}
		}


		for (Room cand : candidates) {
			if (moveToTargets.contains(cand)) {
				return cand;
			}
		}

		return null;
	}
}
