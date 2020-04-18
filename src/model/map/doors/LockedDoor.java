package model.map.doors;

import graphics.sprites.Sprite;

public class LockedDoor extends Door {
    private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);

    public LockedDoor(double x, double y, int fromID, int toID) {
        super(x, y, "Locked", fromID, toID);
    }

    @Override
    protected Sprite getSprite() {
        return LOCKED_DOOR;
    }
}
