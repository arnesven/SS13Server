package model.npcs.behaviors;

import model.map.rooms.Room;
import model.npcs.GalacticFederalMarshalNPC;
import model.npcs.NPC;
import util.MyRandom;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 17/11/16.
 */
public class MoveTowardsMostWantedIfClose implements MovementBehavior {
    private final GalacticFederalMarshalNPC gfm;

    public MoveTowardsMostWantedIfClose(GalacticFederalMarshalNPC npc) {
        this.gfm = npc;
    }

    @Override
    public void move(NPC npc) {
        if (gfm.getMostWanted().getPosition() == npc.getPosition()) {
            // stand still and call for backup!
        } else {
            Set<Room> goToRooms = new HashSet<>();
            for (Room r : npc.getPosition().getNeighborList()) {
                if (gfm.getMostWanted().getPosition() == r) {
                    npc.moveIntoRoom(r);
                    return;
                }
                if (npc.getSpeed() >= 2) {
                    goToRooms.addAll(r.getNeighborList());
                }
                goToRooms.add(r);
            }

            npc.moveIntoRoom(MyRandom.sample(goToRooms));


        }
    }
}
