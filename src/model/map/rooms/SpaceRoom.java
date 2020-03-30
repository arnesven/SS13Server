package model.map.rooms;

import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

/**
 * Created by erini02 on 15/09/17.
 */
public class SpaceRoom extends Room {
    public SpaceRoom(int id, int x, int y, int w, int h) {
        super(id, "Deep Space", x, y, w, h, new int[]{}, new double[]{});
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("outdoorfloor", 18, 24);
    }

}
