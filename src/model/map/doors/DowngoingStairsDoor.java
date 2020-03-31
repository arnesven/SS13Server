package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;

public class DowngoingStairsDoor extends Door {
    public DowngoingStairsDoor(double x, double y) {
        super(x, y, "downstairs");
    }

    @Override
    protected Sprite getSprite() {
        return new Sprite("downstairsdoor", "floors.png", 13, 24, this);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getSprite();
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Downstairs";
    }

}
