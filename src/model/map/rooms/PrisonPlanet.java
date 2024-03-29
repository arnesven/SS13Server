package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.PlanetFloorSet;

public class PrisonPlanet extends OrbitalPlanetRoom {
    public PrisonPlanet(int i) {
        super(i, "Prison Planet", "P R I S O N P L A N E T",
                0, 0, 10, 10,
                new int[]{30}, new Door[]{});
    }

    @Override
    public FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }

    @Override
    protected int getOrbitSpriteRow() {
        return 0;
    }

    @Override
    protected int getOrbitSpriteCol() {
        return 2;
    }

}
