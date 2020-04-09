package model.map.floors;

import graphics.sprites.Sprite;

public class BurntFloorSet extends FloorSet {
    public BurntFloorSet(FloorSet floorSet) {
        super(floorSet.getName(), floorSet.getColumn(), floorSet.getRow());
        makeDirty();
    }

    private void makeDirty() {
        Sprite main = getMainSprite();


    }
}
