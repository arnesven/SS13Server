package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.PlanetFloorSet;

public class PlanetRoom extends Room {
    public PlanetRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    protected String getAppearanceScheme() {
        return "NoWallsNoDoors-Planet";
    }

    @Override
    public FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }
}
