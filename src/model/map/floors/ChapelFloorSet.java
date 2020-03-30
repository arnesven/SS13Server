package model.map.floors;

import graphics.sprites.Sprite;

public class ChapelFloorSet extends FloorSet {
    public ChapelFloorSet() {
        super("chapelfloor", 18, 11);
        setMainSprite(new Sprite("chapelfloor", "floors.png",0, 12, null));
    }

    protected ChapelFloorSet(String name) {
        super(name, 18, 11);
        setMainSprite(new Sprite(name, "floors.png",0, 12, null));
    }
}
