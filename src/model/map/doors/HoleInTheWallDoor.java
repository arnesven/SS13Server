package model.map.doors;

import graphics.sprites.Sprite;

public class HoleInTheWallDoor extends Door {
    public HoleInTheWallDoor(double x, double y, int fromID, int toID) {
        super(x, y, "hole", fromID, toID);
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("holedoor", "floors.png", 10, 9, null);
    }
}
