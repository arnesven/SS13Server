package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;

public class CrackedWallDecoration extends DecorationObject {
    public CrackedWallDecoration(Room position, double xpos, double ypos) {
        super("Cracked Wall", position);
        setAbsolutePosition(xpos, ypos);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("crackedwall", "floors.png", 10, 10, this);
    }
}
