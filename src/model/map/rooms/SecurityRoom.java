package model.map.rooms;

public abstract class SecurityRoom extends Room {
    public SecurityRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new FloorSet("floorsecurity", 27, 0);
    }
}
