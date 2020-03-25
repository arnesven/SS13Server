package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class Pedestals extends GameObject {
    private boolean isBurning;

    public Pedestals(String nameSuffix, Room position) {
        super("Pedestal " + nameSuffix, position);
        isBurning = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (!isBurning) {
            return new Sprite("pedestalnotburning", "altars.png", 21, this);
        }
        return new AnimatedSprite("pedestalburning", "altars.png", 0, 0, 32, 32, this, 8);
    }

    public void setBurning(boolean b) {
        this.isBurning = b;
    }
}
