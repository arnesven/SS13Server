package model.npcs.behaviors;

import model.map.rooms.Room;
import model.map.rooms.SickbayRoom;
import model.npcs.NPC;

public class StayCloseToSickbayMovementBehavior extends MeanderingMovement {
    private Room sick;

    public StayCloseToSickbayMovementBehavior() {
        super(0.0);
    }

    @Override
    public void move(NPC npc) {
        if (npc.getPosition() instanceof SickbayRoom) {
            this.sick = npc.getPosition();
        }

        if (isAroundSickbay(npc)) {
            setProbability(0.5);
            super.move(npc);
        } else if (sick != null) {
            Room r = PathFinding.findCloserRoom(npc, sick);
            npc.moveIntoRoom(r);
        }
    }

    private boolean isAroundSickbay(NPC npc) {
        if (npc.getPosition() instanceof SickbayRoom) {
            return true;
        }
        for (Room r : npc.getPosition().getNeighborList()) {
            if (r instanceof SickbayRoom) {
                return true;
            }
        }
        return false;
    }
}
