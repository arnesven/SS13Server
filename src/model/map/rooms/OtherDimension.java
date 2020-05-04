package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends RemoteRoom {
    public OtherDimension(int id, int[] ints, Door[] doors) {
        super(id, "Other Dimension", "", 0, 0, 10, 10,ints, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("otherdimension", 13, 17);
    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }
}
