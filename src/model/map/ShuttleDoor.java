package model.map;

import graphics.sprites.Sprite;
import model.map.doors.Door;

public class ShuttleDoor extends Door {

    private static Sprite sp = new Sprite("shuttledoor", "doors.png", 4,4, null);

    public ShuttleDoor(double x, double y) {
        super(x, y, "shuttle");
    }

    @Override
    protected Sprite getSprite() {
        return sp;
    }
}
