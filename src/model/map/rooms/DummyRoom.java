package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class DummyRoom extends Room {
        public DummyRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Dummy", x, y, w, h, ints, doubles);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("dummyfloor", 0, 0);
    }
}
