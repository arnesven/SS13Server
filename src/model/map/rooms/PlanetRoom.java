package model.map.rooms;

import graphics.sprites.Sprite;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.PlanetFloorSet;
import model.objects.power.LifeSupport;
import model.objects.power.Lighting;

public class PlanetRoom extends Room {
    public PlanetRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }

    @Override
    protected String getBackgroundStyle() {
        return "Planet";
    }

    @Override
    public FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }

    @Override
    public LifeSupport getLifeSupport() {
        return null;
    }

    @Override
    public Lighting getLighting() {
        return null;
    }

    @Override
    public boolean startsWithPressure() {
        return true;
    }
}
