package model.map.floors;

import graphics.sprites.Sprite;

public class AirLockFloorSet extends FloorSet {
    public AirLockFloorSet() {
        super("airlockfloor", 29, 14);
        setMainSprite(new Sprite("airlockfloor", "floors.png", 2, 5, null));
    }
}
