package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class SolarPanelRoom extends DecorativeRoom {
    public SolarPanelRoom(int id, int x, int y, int w, int h) {
        super(id, "Solar Panels", x, y, w, h, new int[]{}, new Door[]{});
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("solarpanelfloor", 1, 2, "lattice.png");
    }

    @Override
    protected String getAppearanceScheme() {
        return "NoWallsNoDoors-Space";
    }
}
