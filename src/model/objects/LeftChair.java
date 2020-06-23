package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ThroneRoom;
import model.objects.general.GameObject;

public class LeftChair extends GameObject {
    public LeftChair(Room throneRoom) {
        super("Chair Left", throneRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return  new Sprite("chairleft", "furniture2.png", 2, 6, this);
    }
}
