package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ThroneRoom;
import model.objects.general.GameObject;

public class RightChair extends GameObject {
    public RightChair(Room r) {
        super("Chair Right", r);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("chairright", "furniture2.png", 1, 6, this);
    }
}
