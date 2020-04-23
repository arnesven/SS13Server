package model.map;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

import java.awt.*;

public class StraightPowerCoord extends GameItem {
    private final Color col;

    public StraightPowerCoord(Color col) {
        super("Straight Coord", 0, false, 0);
        this.col = col;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = new Sprite("straightpowercord" + col.getRed() + "-" + col.getGreen() + "-" + col.getBlue(),
                "power_cords.png", 0, 1, this);
        sp.setColor(col);
        return sp;
    }

    @Override
    public GameItem clone() {
        return null;
    }
}
