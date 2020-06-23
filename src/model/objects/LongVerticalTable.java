package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ThroneRoom;
import model.objects.general.GameObject;

public class LongVerticalTable extends GameObject {
    public LongVerticalTable(Room throneRoom) {
        super("Table", throneRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("vertitable", "long_table.png", 0, 0, 32, 64, this);
    }
}
