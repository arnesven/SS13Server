package model.map.doors;

import graphics.sprites.Sprite;

public class UpgoingStairsDoor extends Door {
    public UpgoingStairsDoor(double x, double y) {
        super(x, y, "upstairs");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("upstairsdoor", "floors.png", 9, 24, this);
    }
}
