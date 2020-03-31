package model.map.doors;

import graphics.sprites.Sprite;

public class LockedDoor extends Door {
    private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);

    public LockedDoor(double x, double y) {
        super(x, y, "locked");
    }

    @Override
    protected Sprite getSprite() {
        return LOCKED_DOOR;
    }
}
