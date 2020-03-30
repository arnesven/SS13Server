package model.map.floors;

import graphics.sprites.Sprite;

public class PlanetFloorSet extends FloorSet {
    public PlanetFloorSet() {
        super("planetoutdoorfloor", 24, 23);
        setMainSprite(new Sprite("planetoutdoorfloor", "floors.png", 24, 23, null));
    }
}
