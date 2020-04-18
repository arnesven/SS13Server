package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class RemoteRoom extends Room {
    public RemoteRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("outdoorouterfloor", 18, 24);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }
}
