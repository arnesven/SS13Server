package model.map.rooms;

import model.items.weapons.LaserSword;
import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.floors.CaptainsQuartersFloorSet;
import model.map.floors.FloorSet;
import model.objects.decorations.NiceBed;

public class EastwoodsChambers extends NewAlgiersRoom {
    public EastwoodsChambers(int i, int i1, int i2, int i3, int i4, int[] ints, Door[] doors) {
        super(i, "Eastwood's Chambers", i1, i2, i3, i4, ints, doors);
        addObject(new DowngoingStairsDoor(this), RelativePositions.MID_BOTTOM);
        this.addObject(new NiceBed(this));
        this.addItem(new LaserSword());
    }

    @Override
    public FloorSet getFloorSet() {
        return new CaptainsQuartersFloorSet();
    }
}
