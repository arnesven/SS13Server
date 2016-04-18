package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.Room;
import model.npcs.NPC;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
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
        for (Room r : gameData.getRooms()) {
            if (r.hasHullBreach()) {
                brokenList.add(r);
            } else {
                for (GameObject ob : r.getObjects()) {
                    if (ob instanceof BreakableObject) {
                        if (((BreakableObject)ob).isDamaged() || ((BreakableObject)ob).isBroken()) {
                            brokenList.add(r);
                        }
                    }
                }
            }
            for (Actor a : gameData.getActors()) {
                if (a != npc && (a instanceof Repairable) && ((Repairable) a).isDamaged()) {
                    if (!brokenList.contains(r)) {
                        brokenList.add(r);
                    }
                }
            }
        }


        return brokenList;
    }
}
