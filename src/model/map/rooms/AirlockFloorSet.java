package model.map.rooms;

import graphics.sprites.Sprite;

public class AirlockFloorSet extends FloorSet {
    public AirlockFloorSet(String s, int col, int row) {
        super(s, col, row);
        setMainSprite(new Sprite(s, "floors.png", 26, 28, null));
    }
}
