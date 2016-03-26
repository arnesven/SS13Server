package model.npcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.map.Room;

public class AvoidRoomsMovement implements MovementBehavior {

	private List<Room> avoidRooms;
	private MeanderingHumanMovement meander = new MeanderingHumanMovement(1.0);

	public AvoidRoomsMovement(List<Room> list) {
		this.avoidRooms = list;
	}

	@Override
	public void move(NPC npc) {
		if (avoidRooms.contains(npc.getPosition())) {
			System.out.print(npc.getBaseName() + " trying to avoid radiation ");
			List<Room> legalRooms = new ArrayList<>();
			legalRooms.addAll(npc.getPosition().getNeighborList());
			Collections.shuffle(legalRooms);
			for (Room r : legalRooms) {
				if (!avoidRooms.contains(r)) {
					System.out.println(" found refuge in " + r.getName());
					npc.moveIntoRoom(r);
					return;
				}
			}
			System.out.println("but couldn't, moving npc to another room.");
			meander.move(npc);
		} else {
			// do not move, we are safe from radiation here..
		}
		
	}

}
