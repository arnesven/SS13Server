package model.map;

import model.objects.Cabinet;

/**
 * Created by erini02 on 28/04/16.
 */
public class BarRoom extends Room {
    public BarRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType support) {
        super(i, "Bar", "Bar", i1, i2, i3, i4, ints, doubles, support);
        this.addObject(new Cabinet(this));
    }
}
