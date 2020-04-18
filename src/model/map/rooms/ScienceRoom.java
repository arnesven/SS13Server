package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.ScienceFloorSet;

public abstract class ScienceRoom extends Room {

    public ScienceRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new ScienceFloorSet("floorscience", 2, 3);
    }
}
