package model.map.rooms;

import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class VentilationShaftRoom extends Room {
    public VentilationShaftRoom(int ID, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, "Vent Shaft #1", x, y, width, height, neighbors, doors);
        setZ(-1);
    }

    @Override
    protected String getAppearanceScheme() {
        return "Compact-Black";
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("ventshaftfloor", 13, 9);
    }
}
