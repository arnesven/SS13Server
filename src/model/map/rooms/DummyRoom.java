package model.map.rooms;

public class DummyRoom extends Room {
        public DummyRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Dummy", "", x, y, w, h, ints, doubles);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("dummyfloor", 0, 0);
    }
}
