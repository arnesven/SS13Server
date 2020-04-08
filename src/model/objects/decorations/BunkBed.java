package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.DormsRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class BunkBed extends GameObject {
    public BunkBed(Room room) {
        super("Bunk Beds", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bunkbeds", "furniture.png", 0, this);
    }
}
