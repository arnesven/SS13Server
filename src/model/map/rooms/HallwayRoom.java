package model.map.rooms;

import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.floors.FloorSet;
import model.map.floors.HallwayFloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class HallwayRoom extends StationRoom {
    public HallwayRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new HallwayFloorSet();
    }
}
