package model.map.doors;

import graphics.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class ScienceDoor extends ElectricalDoor {

    private static final Sprite UNPOWERED_SPRITE = new Sprite("sciencedoor", "doors.png", 14, null);

    public ScienceDoor(double v, double v1, int i, int i1, boolean b) {
        super(v, v1, "Science", i, i1, b);
    }

    @Override
    public Sprite getLockedSprite() {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(UNPOWERED_SPRITE);
        sprs.add(new Sprite("lockedoverlay", "door_overlays.png", 4, 1, this));
        return new Sprite("lockedsciencedoor", "human.png", 0, sprs, this);
    }

    @Override
    public Sprite getNormalSprite() {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(UNPOWERED_SPRITE);
        sprs.add(new Sprite("normaloverlay", "door_overlays.png", 0, this));
        return new Sprite("normalsciencedoor", "human.png", 0, sprs, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("brokensciencedoor", "doors.png", 15, 1, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return UNPOWERED_SPRITE;
    }

    @Override
    public Sprite getErrorSprite() {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(UNPOWERED_SPRITE);
        sprs.add(new Sprite("erroroverlay", "door_overlays.png", 4, 2, this));
        return new Sprite("errorsciencedoor", "human.png", 0, sprs, this);
    }
}
