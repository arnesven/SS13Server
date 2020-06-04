package model.map.floors;

import graphics.sprites.Sprite;

public class ScienceFloorSet extends FloorSet {
    public ScienceFloorSet() {
        super("floorscience", 2, 3);
        setMainSprite(new Sprite("floorscience", "floors.png", 17, 0, null));
    }


}
