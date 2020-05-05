package model.map.rooms;

import model.map.doors.Door;

public class AirtunnelRoom extends HallwayRoom {
    public AirtunnelRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, shortname, x, y, width, height, neighbors, doors);
    }

    @Override
    protected String getWallAppearence() {
        return "light";
    }
}
