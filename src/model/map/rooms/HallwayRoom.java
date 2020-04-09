package model.map.rooms;

import model.map.doors.DowngoingStairsDoor;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class HallwayRoom extends Room {
    public HallwayRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("floorhall", 0, 0);
    }
}
