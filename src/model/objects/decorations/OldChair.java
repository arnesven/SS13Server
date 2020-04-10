package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class OldChair extends GameObject {
    public OldChair(Room room) {
        super("Old Chair", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("oldchair", "furniture2.png", 3, 3, this);
    }
}
