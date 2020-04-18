package model.map.rooms;

import model.map.doors.Door;
import model.objects.general.SeedVendingMachine;

/**
 * Created by erini02 on 15/12/16.
 */
public class PanoramaRoom extends HallwayRoom {
    public PanoramaRoom(int id, int x, int y, int width, int height, int[] ints, Door[] doubles) {
        super(id, "Panorama Walkway", "", x, y, width, height, ints, doubles);
        this.addObject(new SeedVendingMachine(this));

    }
}
