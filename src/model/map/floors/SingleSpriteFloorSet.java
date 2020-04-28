package model.map.floors;

import graphics.sprites.Sprite;

public class SingleSpriteFloorSet extends FloorSet {


    private final int col;
    private final int row;

    public SingleSpriteFloorSet(String name, int column, int row, String mapPath) {
        super(name, column, row, mapPath);
        this.col = column;
        this.row = row;
    }

    public SingleSpriteFloorSet(String name, int column, int row) {
        super(name, column, row);
        this.col = column;
        this.row = row;
    }


    @Override
    protected void makeSet(int column, int row) {
        for (String part : SET_NAMES) {
            addOtherSprite(new Sprite(getName() + part, getMapPath(), column, row, null));
        }
    }

    @Override
    public Sprite getMainSprite() {
        return  new Sprite(getName(), getMapPath(), col, row, null);
    }
}
