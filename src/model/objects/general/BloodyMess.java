package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;

public class BloodyMess extends GameObject {
    public static final double SPAWN_CHANCE = 0.10;

    public BloodyMess(Room position) {
        super("Bloody Mess", position);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bloodymess", "blood.png", 8, 9);
    }
}
