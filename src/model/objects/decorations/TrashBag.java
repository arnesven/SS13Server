package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class TrashBag extends GameObject {
    public TrashBag(Room room) {
        super("Trash Bag", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("trashbag", "janitor.png", 0, 4, this);
    }
}
