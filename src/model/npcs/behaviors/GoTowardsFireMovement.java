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
public class GoTowardsFireMovement extends GoTowardsRoomMovement {
    private final GameData gameData;

    public GoTowardsFireMovement(GameData gameData) {
        super(gameData);
        this.gameData = gameData;
    }

    @Override
    protected List<Room> getEligableRooms(NPC npc, GameData gameData) {
        List<Room> list = new ArrayList<>();
        for ( Room r : gameData.getRooms()) {
            if (r.hasFire()) {
                list.add(r);
            }
        }
        return list;
    }
}
