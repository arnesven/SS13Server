package model.npcs.behaviors;

import model.GameData;
import model.map.GameMap;
import model.map.Room;
import model.npcs.NPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by erini02 on 15/04/16.
 */
public class GoTowardsFireMovement implements MovementBehavior {
    private final GameData gameData;

    public GoTowardsFireMovement(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void move(final NPC npc) {
       List<Room> fireRooms = new ArrayList<>();
        for ( Room r : gameData.getRooms()) {
            if (r.hasFire()) {
                fireRooms.add(r);
            }
        }
        if (fireRooms.size() == 0) {
            System.out.println("No fires... standing still");
        }

        Collections.sort(fireRooms, new Comparator<Room>() {
            @Override
            public int compare(Room room, Room t1) {
                return GameMap.shortestDistance(npc.getPosition(), room) -
                        GameMap.shortestDistance(npc.getPosition(), t1);
            }
        });

        Room closerRoom = PathFinding.findCloserRoom(npc, fireRooms.get(0));
        if (npc.getPosition() != closerRoom) {
            npc.moveIntoRoom(closerRoom);
        }
    }
}
