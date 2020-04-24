package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;

public class CommandLockedDoor extends LockedDoor {
    public CommandLockedDoor(double x, double y, int fromId, int toId) {
        super(x, y, fromId, toId);
        setName("Locked Command");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("lockedcommanddoor", "doors.png", 8, 15, this);
    }

    @Override
    protected NormalDoor getNormalCounterpart() {
        return new CommandDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected ElectricalDoor getPoweredCounterpart() {
        return new CommandLockedDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected UnpoweredDoor getUnpoweredCounterpart() {
        return new UnpoweredCommandDoor(getX(), getY(), getFromId(), getToId(), true);
    }
}
