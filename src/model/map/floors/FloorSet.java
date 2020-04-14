package model.map.floors;

import graphics.sprites.Sprite;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FloorSet implements Serializable {
    protected static final String[] SET_NAMES = new String[]{"BOTTOM", "TOP", "RIGHT", "LEFT",
            "LR", "LL", "UR", "UL", "ILR", "ILL", "IUR", "IUL"};
    private final String name;
    private final int column;
    private final int row;
    private Sprite mainSprite;
    private List<Sprite> otherSprites;

    public FloorSet(String name, int column, int row) {
        this.name = name;
        this.column = column;
        this.row = row;
        otherSprites = new ArrayList<>();
        this.mainSprite = new Sprite(this.name, "floors.png", 0, 0, null);
        makeSet(column, row);
    }

    protected void makeSet(int column, int row) {
        for (String part : SET_NAMES) {
            otherSprites.add(new Sprite(this.name + part, "floors.png", column, row, null));
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

    protected int getRow() {
        return row;
    }

    protected int getColumn () {
        return column;
    }

    public void registerSprites() {
        for (Sprite s : otherSprites) {
            if (!s.isRegistered()) {
                s.registerYourself();
            }
        }
        if (!mainSprite.isRegistered()) {
            mainSprite.registerYourself();
        }
    }

    protected void addOtherSprite(Sprite sprite) {
        otherSprites.add(sprite);
    }

    protected List<Sprite> getOtherSprites() {
        return otherSprites;
    }

    protected void setOtherSprites(List<Sprite> newOthers) {
        otherSprites = newOthers;
    }

    private Sprite getSpriteWithName(String name) {
        for (Sprite sp : otherSprites) {
            if (sp.getName().contains(name)) {
                return sp;
            }
        }
        return null;
    }

    public Sprite getUpperLeft() {
        return getSpriteWithName("UL");
    }

    public Sprite getUpperRight() {
        return getSpriteWithName("UR");
    }

    public Sprite getTop() {
        return getSpriteWithName("TOP");
    }

    public Sprite getLowerLeft() {
        return getSpriteWithName("LL");
    }

    public Sprite getLowerRight() {
        return getSpriteWithName("LR");
    }

    public Sprite getBottom() {
        return getSpriteWithName("BOTTOM");
    }

    public Sprite getLeft() {
        return getSpriteWithName("LEFT");
    }

    public Sprite getRight() {
        return getSpriteWithName("RIGHT");
    }
}
