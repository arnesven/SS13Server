package model.map.rooms;

import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

/**
 * Created by erini02 on 09/09/17.
 */
public class BaseStarRoom extends Room {
    public BaseStarRoom(int id, int x, int y, int w, int h) {
        super(id, "Basestar Bridge", x, y, w, h, new int[]{}, new double[]{});
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("basestarfloor", 9, 19);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }
}
