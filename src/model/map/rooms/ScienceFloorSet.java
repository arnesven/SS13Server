package model.map.rooms;

import graphics.sprites.Sprite;

public class ScienceFloorSet extends FloorSet {
    public ScienceFloorSet(String name, int col, int row) {
        super(name, col, row);
        setMainSprite(new Sprite(name, "floors.png", 17, 0, null));
    }


}
