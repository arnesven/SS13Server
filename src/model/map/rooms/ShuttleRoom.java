package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.consoles.ShuttleControl;

/**
 * Created by erini02 on 17/09/17.
 */
public class ShuttleRoom extends Room {
    public ShuttleRoom(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, Door[] doubles, GameData gameData) {
        super(id, name, x, y, w, h, ints, doubles);
        this.addObject(new ShuttleControl(this));
    }

    public void moveTo(int x, int y, int z) {
        setCoordinates(x, y, z);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("shuttlefloor", 19, 28);
    }
}
