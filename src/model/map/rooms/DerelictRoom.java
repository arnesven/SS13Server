package model.map.rooms;

public class DerelictRoom extends Room {
    public DerelictRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, shortname, x, y, width, height, neighbors, doors);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("derelictfloorhall", 13, 9);
    }
}
