package model.npcs.behaviors;

import model.GameData;
import model.map.GameMap;
import model.map.rooms.Room;
import model.npcs.NPC;
import util.Logger;

import java.util.*;

/**
 * Created by erini02 on 18/04/16.
 */
public abstract class GoTowardsRoomMovement implements MovementBehavior{

    private final GameData gameData;

    public GoTowardsRoomMovement(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void move(final NPC npc) {
        List<Room> fireRooms = getEligableRooms(npc, gameData);
        if (fireRooms.size() == 0) {
            Logger.log("No eligible rooms... standing still");
            return;
        }

        Map<Room, Integer> distances = PathFinding.getDistanceMap(fireRooms, npc.getPosition());


        Collections.sort(fireRooms, new Comparator<Room>() {
            @Override
            public int compare(Room room, Room t1) {
                return distances.get(room) - distances.get(t1);
            }
        });

        Room closerRoom = PathFinding.findCloserRoom(npc, fireRooms.get(0));
        if (npc.getPosition() != closerRoom) {
            npc.moveIntoRoom(closerRoom);
        }
    }

    protected abstract List<Room> getEligableRooms(NPC npc, GameData gameData);

    public boolean isDone(NPC npc, GameData gameData) {
        Logger.log("Size of eligable rooms: " + getEligableRooms(npc, gameData).size());


        if (getEligableRooms(npc, gameData).size() == 1 && getEligableRooms(npc, gameData).get(0) == npc.getPosition()) {
            Logger.log("At position: " + npc.getPosition());
            return true;
        }
        return false;
    }
}
