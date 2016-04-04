package model.npcs;

import java.util.HashSet;
import java.util.Set;

import model.Actor;
import model.GameData;
import model.map.GameMap;
import model.map.Room;
import model.objects.CrimeRecordsConsole;

public class FollowCriminalBehavior implements MovementBehavior {

	private CrimeRecordsConsole console;

	public FollowCriminalBehavior(GameData gameData) {
		console = CrimeRecordsConsole.find(gameData);
		
	}

	@Override
	public void move(NPC npc) {
		Actor mostWanted = console.getMostWanted();
		if (mostWanted == null) {
			System.out.println("SecuriTRON: no bad guys right now, guess I'll go back to sleep.");
			return;
		}
		
		System.out.println("SecuriTRON: Most wanted criminal is " + mostWanted.getBaseName() +", you're going down!");
		
		Room closerRoom = findCloserRoom(npc, mostWanted.getPosition());
		if (npc.getPosition() != closerRoom) {
			npc.moveIntoRoom(closerRoom);
		}
	}

	private Room findCloserRoom(NPC npc, Room r) {
		System.out.println(" SecuriTRON: Finding closest room to " + r.getName());
		Set<Room> rooms = new HashSet<>();
		System.out.println(" SecuriTRON: my move steps are " + npc.getCharacter().getMovementSteps());
		recursivelyGetAccissibleRooms(npc.getPosition(), rooms, npc.getCharacter().getMovementSteps());
		
		Room best = null;
		int least = 10000;
		for (Room r2 : rooms) {
			int dist = GameMap.shortestDistance(r2, r);
			System.out.println(" SecuriTRON: a room I can go to: " + r2.getName());
			System.out.println(" SecuriTRON: it's " + dist + " rooms away");
			
			if (dist < least) {
				least = dist;
				best = r2;
			}
		}
		System.out.println(" SecuriTRON: Best room to go to is " + best.getName());
		
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
