package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import sounds.Sound;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends RemoteRoom {
    public OtherDimension(int id, int[] ints, Door[] doors) {
        super(id, "Other Dimension", "", 0, 0, 5, 5, ints, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("otherdimension",  10, 19, "weapons2.png");
    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }

    @Override
    protected String getBackgroundStyle() {
        return "Black";
    }

    @Override
    protected Sound getSpecificAmbientSound() {
        return new Sound("ambireebe1");
    }
}
