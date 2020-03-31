package model.map.doors;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class NormalDoor extends Door {

     private static final Sprite NORMAL_DOOR = new Sprite("normaldoor", "doors.png", 11, 17, null);


    public NormalDoor(double x, double y) {
        super(x, y, "normal");
    }


    @Override
    protected Sprite getSprite() {
        return NORMAL_DOOR;
    }
}
