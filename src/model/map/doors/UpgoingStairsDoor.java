package model.map.doors;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class UpgoingStairsDoor extends StairsObject {
    public UpgoingStairsDoor(Room pos) {
        super("Stairs Up", pos);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("upstairsdoor", "floors.png", 9, 24, this);
    }
}
