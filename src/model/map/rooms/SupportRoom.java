package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;

public class SupportRoom extends StationRoom {
    public SupportRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("floorsupport", 11, 7);
    }
}
