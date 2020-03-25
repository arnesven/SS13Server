package model.map.rooms;

import graphics.sprites.Sprite;

public class ImprovedChapelFloorSet extends ChapelFloorSet {
    public ImprovedChapelFloorSet(String newName, int col) {
        super(newName);
        setMainSprite(new Sprite(newName, "floors.png", col, getRow()+1, null));
    }
}
