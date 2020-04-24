package model.map.doors;

import graphics.sprites.Sprite;

public class UnpoweredCommandDoor extends UnpoweredDoor {
    public UnpoweredCommandDoor(double x, double y, int fromId, int toId, boolean wasLocked) {
        super(x, y, fromId, toId, wasLocked);
        setName("Unpowered Command");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("unpoweredcommanddoor", "doors.png", 7, 15, this);
    }

    @Override
    protected NormalDoor getBrokenCounterpart() {
        return new CommandDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected ElectricalDoor getPoweredCounterpart() {
        if (wasLocked()) {
            return new CommandLockedDoor(getX(), getY(), getFromId(), getToId());
        }
        return getBrokenCounterpart();
    }
}
