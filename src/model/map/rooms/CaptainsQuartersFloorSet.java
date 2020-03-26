package model.map.rooms;

import graphics.sprites.Sprite;

public class CaptainsQuartersFloorSet extends FloorSet {
    public CaptainsQuartersFloorSet() {
        super("floorCQ", 24, 24);
    }

    protected void makeSet(int column, int row) {
        for (String part :  new String[]{"UL", "TOP", "UR", "LEFT",
                "", "RIGHT", "LL", "BOTTOM", "LR"}) {
            new Sprite(this.getName() + part, "floors.png", column, row, null);
            column++;
            if (column == 30) {
                column = 0;
                row++;
            }
        }
    }
}
