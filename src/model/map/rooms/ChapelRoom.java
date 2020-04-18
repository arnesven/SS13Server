package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.Altar;

/**
 * Created by erini02 on 15/12/16.
 */
public class ChapelRoom extends SupportRoom {
    public ChapelRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Chapel", "Chap", x, y, w, h, ints, doubles);
        Altar altar = new Altar(this);
        addObject(altar);
    }

    @Override
    public FloorSet getFloorSet() {
        return new ChapelFloorSet();
    }
}
