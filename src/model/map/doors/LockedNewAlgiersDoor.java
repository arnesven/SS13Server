package model.map.doors;

import graphics.sprites.Sprite;

public class LockedNewAlgiersDoor extends Door {
    public LockedNewAlgiersDoor(double x, double y, int fromID, int toID) {
        super(x, y, 0.0, "New Algiers Door", fromID, toID);
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("newalgiersdoor", "newalgiersdoor.png", 0, 0, this);
    }

    @Override
    protected Sprite getFogOfWarSprite() {
        return this.getSprite();
    }
}
