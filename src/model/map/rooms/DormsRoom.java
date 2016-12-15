package model.map.rooms;

import model.objects.StasisPod;
import model.objects.general.Lockers;

/**
 * Created by erini02 on 15/12/16.
 */
public class DormsRoom extends Room {
    public DormsRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType support) {
        super(i, "Dorms", "Dorm", i1, i2, i3, i4, ints, doubles, support);
        addObject(new Lockers(this));
        addObject((new StasisPod(this)));
    }
}
