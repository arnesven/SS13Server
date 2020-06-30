package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class FixedShuttleThruster extends GameObject {
    public FixedShuttleThruster(Room room) {
        super("Thruster", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("thrusterleft", "shuttle.png", 12, 13, this);
    }
}
