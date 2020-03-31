package model.map.doors;

import graphics.sprites.Sprite;

public class HoleInTheWallDoor extends Door {
    public HoleInTheWallDoor(double x, double y) {
        super(x, y, "hole");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("holedoor", "floors.png", 10, 9, null);
    }
}
