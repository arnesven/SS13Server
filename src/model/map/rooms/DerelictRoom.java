package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.power.LifeSupport;

public class DerelictRoom extends Room {
    public DerelictRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("derelictfloorhall", 13, 9);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }

    @Override
    public boolean startsWithPressure() {
        return false;
    }

}
