package model.map.doors;

import graphics.sprites.Sprite;

public class EngineeringDoor extends ElectricalDoor {
    public EngineeringDoor(double x, double y, double z, int fromId, int toId, boolean b) {
        super(x, y, z, "Engineering", fromId, toId, b);
    }

    public EngineeringDoor(double v, double v1, int i, int i1, boolean locked) {
        this(v, v1, 0.0, i, i1, locked);
    }

    @Override
    public Sprite getLockedSprite() {
        return new Sprite("lockedengdoor.png", "doors.png", 5, 4, this);
    }

    @Override
    public Sprite getNormalSprite() {
        return new Sprite("normalengdoor.png", "doors.png", 9, 4, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("brokenengdoor.png", "doors.png", 5, 5, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return  new Sprite("unpoweredengdoor.png", "doors.png", 4, 4, this);
    }

    @Override
    public Sprite getErrorSprite() {
         return new Sprite("engdoorerror", "doors.png", 14, 19, this);
    }

    @Override
    public ElectricalDoor makeCopy(double x, double y, double z, int fromId, int toId) {
        return new EngineeringDoor(x, y, z, fromId, toId, false);
    }
}
