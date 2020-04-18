package model.map.rooms;

import model.map.doors.Door;

public class ShuttleGateRoom extends HallwayRoom {
    public ShuttleGateRoom(int id, int x, int y, int width, int height, int[] ints, Door[] doubles) {
        super(id, "Shuttle Gate", "Gate"   ,x,  y, width, height, ints, doubles);
    }
}
