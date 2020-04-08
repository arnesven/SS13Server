package model.map.floors;

import graphics.sprites.Sprite;

public class SingleSpriteFloorSet extends FloorSet {


    private final int col;
    private final int row;

    public SingleSpriteFloorSet(String name, int column, int row) {
        super(name, column, row);
        this.col = column;
        this.row = row;
    }

    @Override
    protected void makeSet(int column, int row) {
        for (String part : SET_NAMES) {
            addOtherSprite(new Sprite(getName() + part, "floors.png", column, row, null));
        }
    }

    @Override
    public Sprite getMainSprite() {
        return  new Sprite(getName(), "floors.png", col, row, null);
    }
}
