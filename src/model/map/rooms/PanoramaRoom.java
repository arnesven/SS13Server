package model.map.rooms;

import model.objects.general.SeedVendingMachine;

/**
 * Created by erini02 on 15/12/16.
 */
public class PanoramaRoom extends Room {
    public PanoramaRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType hall) {
        super(i, "Panorama Walkway", "", i1, i2, i3, i4, ints, doubles, hall);
        this.addObject(new SeedVendingMachine(this));

    }
}
