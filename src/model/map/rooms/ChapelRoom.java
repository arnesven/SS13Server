package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.Altar;
import sounds.Sound;

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

    @Override
    protected boolean getsTrashBin() {
        return false;
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambidet2");
    }
}
