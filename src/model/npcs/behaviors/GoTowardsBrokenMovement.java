package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.objects.general.BreakableObject;
import model.objects.general.Repairable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class GoTowardsBrokenMovement extends GoTowardsRoomMovement {
    public GoTowardsBrokenMovement(GameData gameData) {
        super(gameData);
    }

    @Override
    protected List<Room> getEligableRooms(NPC npc, GameData gameData) {
        List<Room> brokenList = new ArrayList<>();
        for (Room r : gameData.getNonHiddenStationRooms()) {
            if (r.hasHullBreach()) {
                brokenList.add(r);
            } else {
                for (BreakableObject ob : r.getBreakableObjects(gameData)) {
                    if (ob.isDamaged() || ob.isBroken()) {
                        brokenList.add(r);
                        break; // Only one such thing needed to be eligible for repair.
                    }
                }
            }

            for (Actor a : gameData.getActors()) {
                if (a != npc && (a instanceof Repairable) && ((Repairable) a).isDamaged()) {
                    if (!brokenList.contains(r)) {
                        brokenList.add(r);
                        break; // Only one such thing needed to be eligible for repair.
                    }
                }
            }
        }


        return brokenList;
    }
}
