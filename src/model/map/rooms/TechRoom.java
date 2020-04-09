package model.map.rooms;

import model.map.floors.FloorSet;

public abstract class TechRoom extends Room {
    public TechRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("floortech", 27, 3);
    }
}
