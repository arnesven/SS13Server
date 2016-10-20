package model.npcs.behaviors;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.map.Room;
import model.npcs.NPC;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 20/10/16.
 */
public class FindHumansMovement implements MovementBehavior {
    @Override
    public void move(NPC npc) {
        if (!aliveHumansInThisRoom(npc.getPosition())) {
            for (Room r : npc.getPosition().getNeighborList()) {
                if (aliveHumansInThisRoom(r)) {
                    npc.moveIntoRoom(r);
                    return;
                }
            }
        }

        if (npc.getPosition().getNeighborList().size() > 0) {
            npc.moveIntoRoom(MyRandom.sample(npc.getPosition().getNeighborList()));
        }
    }

    private boolean aliveHumansInThisRoom(Room r) {
        for (Actor a : r.getActors()) {
            if (a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof HumanCharacter))) {
                if (!a.isDead()) {
                    Logger.log("Found humans to kill in room " + r.getName());
                    return true;
                }
            }
        }
        return false;
    }
}
