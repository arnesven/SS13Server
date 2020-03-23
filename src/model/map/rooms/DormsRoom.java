package model.map.rooms;

import model.objects.general.StasisPod;
import model.objects.general.Lockers;
import model.objects.general.Shower;

/**
 * Created by erini02 on 15/12/16.
 */
public class DormsRoom extends SupportRoom {
    public DormsRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Dorms", "Dorm", x, y, w, h, ints, doubles);
        addObject(new Lockers(this));
        addObject((new StasisPod(this)));
        addObject(new Shower(this));
    }
}
