package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;

public class PrisonPlanet extends RemoteRoom {
    public PrisonPlanet(int i) {
        super(i, "Prison Planet", "P R I S O N P L A N E T",
                0, 0, 10, 10,
                new int[]{30}, new double[]{-1.0, -1.0});
    }

    @Override
    protected FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }
}
