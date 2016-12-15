package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.map.rooms.Room;
import model.npcs.NPC;
import util.Logger;

public class AvoidRoomsMovement implements MovementBehavior {

	private Collection<Room> avoidRooms;
	private MeanderingHumanMovement meander = new MeanderingHumanMovement(1.0);

	public AvoidRoomsMovement(Collection<Room> list) {
		this.avoidRooms = list;
	}

	@Override
	public void move(NPC npc) {
		if (avoidRooms.contains(npc.getPosition())) {
			Logger.log(npc.getBaseName() + " trying to avoid radiation ");
			List<Room> legalRooms = new ArrayList<>();
			legalRooms.addAll(npc.getPosition().getNeighborList());
			Collections.shuffle(legalRooms);
			for (Room r : legalRooms) {
				if (!avoidRooms.contains(r)) {
					Logger.log(" found refuge in " + r.getName());
					npc.moveIntoRoom(r);
					return;
				}
			}
			Logger.log("but couldn't, moving npc to another room.");
			meander.move(npc);
		} else {
			// do not move, we are safe from radiation here..
		}
		
	}

}
