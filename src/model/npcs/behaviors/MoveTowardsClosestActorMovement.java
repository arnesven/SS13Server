package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.Room;
import model.npcs.NPC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class MoveTowardsClosestActorMovement extends GoTowardsRoomMovement {

    public MoveTowardsClosestActorMovement(GameData gameData) {
        super(gameData);
    }

    @Override
    protected List<Room> getEligableRooms(NPC npc, GameData gameData) {
        List<Room> list = new ArrayList<>();
        for (Room r : gameData.getRooms()) {
            for (Actor a :  r.getActors()) {
                if (a != npc) {
                    list.add(r);
                    break;
                }
            }
        }
        return list;
    }
}
