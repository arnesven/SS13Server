package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ThroneRoom;
import model.objects.general.GameObject;

public class PirateFeast extends GameObject {
    public PirateFeast(Room room) {
        super("Pirate's Feast", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("piratesfeast", "long_table.png", 32, 64, 1, 0, this);
    }
}
