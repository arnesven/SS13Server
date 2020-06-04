package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;

public class CommandDoor extends ElectricalDoor {
    public CommandDoor(double x, double y, double z, int fromId, int toId, boolean b) {
        super(x, y, z, "Command", fromId, toId, b);
    }

    public CommandDoor(double v, double v1, int i, int i1, boolean locked) {
        this(v, v1, 0.0, i, i1, locked);
    }


    @Override
    public Sprite getLockedSprite() {
        return new Sprite("lockedcommanddoor", "doors.png", 8, 15, this);
    }

    @Override
    public Sprite getNormalSprite() {
        return new Sprite("commanddoor", "doors.png", 8, 16, this);
    }

    @Override
    public Sprite getErrorSprite() {
        return new Sprite("commanddoorerror", "doors.png", 12, 19, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("commanddoorbroken", "doors.png", 0, 17, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return new Sprite("unpoweredcommanddoor", "doors.png", 7, 15, this);
    }


    @Override
    public ElectricalDoor makeCopy(double x, double y, double z, int fromId, int toId) {
        return new CommandDoor(x, y, z, fromId, toId, false);
    }
}
