package model.map;

import graphics.sprites.Sprite;
import model.map.doors.Door;

public class ShuttleDoor extends Door {
    public ShuttleDoor(double x, double y) {
        super(x, y, "shuttle");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("shuttledoor", "doors.png", 4, 4, this);
    }
}
