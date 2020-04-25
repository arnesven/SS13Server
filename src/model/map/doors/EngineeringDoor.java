package model.map.doors;

import graphics.sprites.Sprite;

public class EngineeringDoor extends ElectricalDoor {
    public EngineeringDoor(double v, double v1, int i, int i1, boolean locked) {
        super(v, v1, "Engineering", i, i1, locked);
    }

    @Override
    public Sprite getLockedSprite() {
        return new Sprite("lockedengdoor.png", "doors.png", 5, 4, this);
    }

    @Override
    public Sprite getNormalSprite() {
        return new Sprite("unpoweredengdoor.png", "doors.png", 9, 4, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("lockedengdoor.png", "doors.png", 5, 5, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return  new Sprite("unpoweredengdoor.png", "doors.png", 4, 4, this);
    }

    @Override
    public Sprite getErrorSprite() {
         return new Sprite("engdoorerror", "doors.png", 14, 19, this);
    }
}
