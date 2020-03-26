package model.map.rooms;

public class SupportRoom extends Room {
    public SupportRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new FloorSet("floorsupport", 11, 7);
    }
}
