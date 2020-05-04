package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.power.LifeSupport;
import model.objects.power.Lighting;

/**
 * Created by erini02 on 15/09/17.
 */
public class SpaceRoom extends Room {
    public SpaceRoom(int id, int x, int y, int w, int h) {
        super(id, "Deep Space", x, y, w, h, new int[]{}, new Door[]{});
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("outdoorfloor", 18, 24);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }

    @Override
    public LifeSupport getLifeSupport() {
        return null;
    }

    @Override
    public Lighting getLighting() {
        return null;
    }
}
