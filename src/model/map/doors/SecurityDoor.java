package model.map.doors;

import graphics.sprites.Sprite;

public class SecurityDoor extends ElectricalDoor {
    public SecurityDoor(double v, double v1, int i, int i1, boolean b) {
        super(v, v1, "Security", i, i1, b);
    }

    @Override
    public Sprite getLockedSprite() {
        return new Sprite("securitylocked", "doors.png", 13, 2, this);
    }

    @Override
    public Sprite getNormalSprite() {
        return new Sprite("securitydoornormal", "doors.png", 1, 3, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("securitydoorbroken", "doors.png", 7, 3, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return new Sprite("securitydoorunpowered", "doors.png", 12, 2, this);
    }

    @Override
    public Sprite getErrorSprite() {
        return new Sprite("securitydoorerror", "doors.png", 13, 19, this);
    }
}
