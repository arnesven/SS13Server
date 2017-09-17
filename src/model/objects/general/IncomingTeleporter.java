package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;

/**
 * Created by erini02 on 17/09/17.
 */
public class IncomingTeleporter extends GameObject {
    public IncomingTeleporter(Room position) {
        super("Teleport Field", position);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("teleportfield", "effects.png", 5, 15);
    }
}
