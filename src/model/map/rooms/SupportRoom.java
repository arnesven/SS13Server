package model.map.rooms;

import model.map.floors.FloorSet;

public class SupportRoom extends Room {
    public SupportRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("floorsupport", 11, 7);
    }
}
