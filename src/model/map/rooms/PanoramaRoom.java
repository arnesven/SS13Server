package model.map.rooms;

import model.objects.general.SeedVendingMachine;

/**
 * Created by erini02 on 15/12/16.
 */
public class PanoramaRoom extends Room {
    public PanoramaRoom(int id, int x, int y, int width, int height, int[] ints, double[] doubles, RoomType hall) {
        super(id, "Panorama Walkway", "", x, y, width, height, ints, doubles, hall);
        this.addObject(new SeedVendingMachine(this));

    }
}
