package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class BurnMark extends GameObject {
    public BurnMark(Room room) {
        super("Burn Mark", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("burnmark", "floors.png", 27, 28, null);
    }
}
