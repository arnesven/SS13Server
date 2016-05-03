package model.npcs.behaviors;

import model.GameData;
import model.map.GameMap;
import model.map.Room;
import model.npcs.NPC;
import util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
            Logger.log("No fires... standing still");
            return;
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

    protected abstract List<Room> getEligableRooms(NPC npc, GameData gameData);
}
