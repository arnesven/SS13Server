package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class WetWarningSign extends GameObject {
    public WetWarningSign(Room room) {
        super("Slippery Floors", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("wetwarningsign", "janitor.png", 0, this);
    }
}
