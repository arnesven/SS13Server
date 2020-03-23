package model.map.rooms;

public abstract class ScienceRoom extends Room {

    public ScienceRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, shortname, x, y, width, height, neighbors, doors);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new ScienceFloorSet("floorscience", 2, 3);
    }
}
