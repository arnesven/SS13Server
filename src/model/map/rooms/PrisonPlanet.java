package model.map.rooms;

import model.map.floors.FloorSet;
import model.map.floors.PlanetFloorSet;

public class PrisonPlanet extends RemoteRoom {
    public PrisonPlanet(int i) {
        super(i, "Prison Planet", "P R I S O N P L A N E T",
                0, 0, 10, 10,
                new int[]{30}, new double[]{-1.0, -1.0});
    }

    @Override
    public FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }
}
