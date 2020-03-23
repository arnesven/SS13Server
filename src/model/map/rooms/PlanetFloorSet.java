package model.map.rooms;

import graphics.sprites.Sprite;

public class PlanetFloorSet extends FloorSet {
    public PlanetFloorSet() {
        super("planetfloor", 24, 23);
        setMainSprite(new Sprite("planetfloor", "floors.png", 24, 23, null));
    }
}
