package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.CommandFloorSet;
import model.map.floors.FloorSet;

public abstract class CommandRoom extends StationRoom {

    public CommandRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new CommandFloorSet();
    }
}
