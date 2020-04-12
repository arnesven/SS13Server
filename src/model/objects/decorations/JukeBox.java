package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.BarRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class JukeBox extends GameObject {
    public JukeBox(Room barRoom) {
        super("JukeBox", barRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("jukebox", "jukebox.png", 0, 2, this);
    }
}
