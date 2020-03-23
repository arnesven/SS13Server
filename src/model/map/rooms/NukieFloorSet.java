package model.map.rooms;

import graphics.sprites.Sprite;

public class NukieFloorSet extends FloorSet {
    public NukieFloorSet() {
        super("floornukieship", 16, 14);
        setMainSprite(new Sprite("floornukieship", "floors.png", 26, 28, null));
    }
}
