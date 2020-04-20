package model.map.doors;

import graphics.sprites.Sprite;

public class AirLockDoor extends Door {
    public AirLockDoor(double x, double y, int fromId, int toId) {
        super(x, y, "Airlock", fromId, toId);
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("airlockdoor", "doors.png", 1, 7, this);
    }
}
