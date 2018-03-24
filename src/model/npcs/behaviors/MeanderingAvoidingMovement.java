package model.npcs.behaviors;

import model.map.rooms.Room;
import model.npcs.NPC;

import java.util.Collection;
import java.util.List;

public class MeanderingAvoidingMovement extends MeanderingHumanMovement {
    private final Collection<Room> roomsToAvoid;

    public MeanderingAvoidingMovement(Collection<Room> avoidRooms) {
        super(0.25);
        this.roomsToAvoid = avoidRooms;
    }

    @Override
    public void move(NPC npc) {
        double old = getProbability();
        if (roomsToAvoid.contains(npc.getPosition())) {
            setProbability(1.0);
        }
        super.move(npc);
        setProbability(old);
    }

    @Override
    protected void filterRooms(List<Room> listOfNeighboringRooms) {
        listOfNeighboringRooms.removeAll(roomsToAvoid);
    }
}
