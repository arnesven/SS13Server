package model.map.floors;

import graphics.sprites.Sprite;
import model.map.floors.ChapelFloorSet;

public class ImprovedChapelFloorSet extends ChapelFloorSet {
    public ImprovedChapelFloorSet(String newName, int col) {
        super(newName);
        setMainSprite(new Sprite(newName, "floors.png", col, getRow()+1, null));
    }
}
