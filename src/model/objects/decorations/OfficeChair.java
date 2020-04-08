package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.OfficeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class OfficeChair extends GameObject {
    public OfficeChair(Room r) {
        super("Office Chair", r);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("Office Chair", "furniture2.png", 7, 4, this);
    }
}
