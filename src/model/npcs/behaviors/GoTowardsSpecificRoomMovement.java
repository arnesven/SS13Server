package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.rooms.Room;
import model.npcs.NPC;

import java.util.ArrayList;
import java.util.List;

public class GoTowardsSpecificRoomMovement extends GoTowardsRoomMovement {
    private final Room room;

    public GoTowardsSpecificRoomMovement(GameData gameData, Room specific) {
        super(gameData);
        this.room = specific;
    }

    @Override
    protected List<Room> getEligableRooms(NPC npc, GameData gameData) {
        List<Room> result = new ArrayList<>();
        result.add(room);
        return result;
    }
}
