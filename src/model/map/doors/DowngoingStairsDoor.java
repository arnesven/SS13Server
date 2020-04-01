package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.map.rooms.Room;

public class DowngoingStairsDoor extends StairsObject {
    public DowngoingStairsDoor(Room pos) {
        super("Stairs Down", pos);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("downstairsdoor", "floors.png", 13, 24, this);
    }

}
