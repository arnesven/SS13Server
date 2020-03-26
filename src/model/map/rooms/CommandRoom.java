package model.map.rooms;

public abstract class CommandRoom extends Room {

    public CommandRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new FloorSet("floorcommand", 23, 1);
    }
}
