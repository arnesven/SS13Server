package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.LoungeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class LongSofa extends GameObject {
    public LongSofa(Room room) {
        super("Sofa", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("sofa", "chairs.png", 1, 11, 64, 32, this);
    }
}
