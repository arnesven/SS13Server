package model.map.rooms;

/**
 * Created by erini02 on 09/09/17.
 */
public class BaseStarRoom extends Room {
    public BaseStarRoom(int id, int x, int y, int w, int h) {
        super(id, "Basestar Bridge", x, y, w, h, new int[]{}, new double[]{});
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("basestarfloor", 9, 19);
    }
}
