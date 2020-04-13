package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class PosterObject extends GameObject  {
    public PosterObject(Room room) {
        super("Poster", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("someposter", "posters2.png", 1, 1, this);
    }
}
