package model.map.rooms;

import model.GameData;
import model.objects.consoles.ShuttleControl;

/**
 * Created by erini02 on 17/09/17.
 */
public class ShuttleRoom extends Room {
    public ShuttleRoom(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, double[] doubles, RoomType type, GameData gameData) {
        super(id, name, shortname, x, y, w, h, ints, doubles, type);
        this.addObject(new ShuttleControl(this));
    }

    public void moveTo(int x, int y) {
        setCoordinates(x, y);
    }
}
