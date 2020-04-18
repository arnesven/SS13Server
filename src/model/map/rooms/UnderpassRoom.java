package model.map.rooms;

import model.map.doors.Door;

public class UnderpassRoom extends HallwayRoom {
    public UnderpassRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doors) {
        super(id, "Underpass", "", x, y, w, h, ints, doors);
        super.setZ(-1);
    }
}
