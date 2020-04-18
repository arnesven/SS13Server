package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;

public abstract class SecurityRoom extends Room {
    public SecurityRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("floorsecurity", 27, 0);
    }
}
