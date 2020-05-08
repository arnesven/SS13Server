package model.npcs.behaviors;

import model.GameData;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.rooms.EscapeShuttle;
import model.map.rooms.Room;
import model.npcs.NPC;

public class GoTowardsEscapeShuttle implements MovementBehavior {
    private final GameData gameData;
    private final DockingPoint dockingPoint;

    public GoTowardsEscapeShuttle(DockingPoint dp, GameData gameData) {
        this.gameData = gameData;
        this.dockingPoint = dp;
    }

    @Override
    public void move(NPC npc) {
        try {
            EscapeShuttle shuttle = (EscapeShuttle)gameData.getRoom("Escape Shuttle");
            Room r;
            if (shuttle.hasArrived()) {
                r = PathFinding.findCloserRoom(npc, shuttle.getDockingPointRoom());
            } else {
                r = PathFinding.findCloserRoom(npc, dockingPoint.getRoom());
            }
            npc.moveIntoRoom(r);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
