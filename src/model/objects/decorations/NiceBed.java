package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.CaptainsQuartersRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class NiceBed extends GameObject {
    public NiceBed(Room r) {
        super("Nice Bed", r);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("nicebed", "furniture2.png", 1, 3, this);
    }
}
