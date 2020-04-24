package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;

public class CommandDoor extends NormalDoor {
    public CommandDoor(double v, double v1, int i, int i1) {
        super(v, v1, i, i1);
        setName("Command");
    }

    @Override
    protected Sprite getNormalSprite() {
        return new Sprite("commanddoor", "doors.png", 8, 16, this);
    }

    @Override
    protected Sprite getErrorDoorSprite() {
        return new Sprite("commanddoorerror", "doors.png", 12, 19, this);
    }

    @Override
    protected Sprite getBrokenDoorSprite() {
        return new Sprite("commanddoorbroken", "doors.png", 0, 17, this);
    }


    protected LockedDoor getLockedCounterpart() {
        return new CommandLockedDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected ElectricalDoor getPoweredCounterpart() {
        return new CommandDoor(getX(), getY(), getFromId(), getToId());
    }

    @Override
    protected UnpoweredDoor getUnpoweredCounterpart() {
        return new UnpoweredCommandDoor(getX(), getY(), getFromId(), getToId(), false);
    }
}
