package model.map.rooms;

import graphics.sprites.Sprite;
import util.Logger;

public class FloorSet {
    protected static final String[] SET_NAMES = new String[]{"BOTTOM", "TOP", "RIGHT", "LEFT",
            "LR", "LL", "UR", "UL", "ILR", "ILL", "IUR", "IUL"};
    private final String name;
    private final int column;
    private final int row;
    private Sprite mainSprite;

    public FloorSet(String name, int column, int row) {
        this.name = name;
        this.column = column;
        this.row = row;
        this.mainSprite = new Sprite(this.name, "floors.png", 0, 0, null);
        makeSet(column, row);
    }

    protected void makeSet(int column, int row) {
        for (String part : SET_NAMES) {
            new Sprite(this.name + part, "floors.png", column, row, null);
            column++;
            if (column == 30) {
                column = 0;
                row++;
            }
        }
    }

    public Sprite getMainSprite() {
        return mainSprite;
    }

    public void setMainSprite(Sprite sp) {
        mainSprite = sp;
    }

    public String getName() {
        return name;
    }

}
