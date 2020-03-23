package model.map.rooms;

import graphics.sprites.Sprite;

public class SingleSpriteFloorSet extends FloorSet {


    public SingleSpriteFloorSet(String name, int column, int row) {
        super(name, column, row);
    }

    @Override
    protected void makeSet(int column, int row) {
        for (String part : SET_NAMES) {
            new Sprite(getName() + part, "floors.png", column, row, null);
        }
    }
}
