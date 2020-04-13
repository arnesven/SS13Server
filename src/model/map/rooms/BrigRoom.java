package model.map.rooms;

import model.objects.decorations.PosterObject;

/**
 * Created by erini02 on 08/12/16.
 */
public class BrigRoom extends SecurityRoom {
    public BrigRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Brig", "", x, y, w, h, ints, doubles);
        this.addObject(new PosterObject(this, "Obey", 1, 13, 0.5));
    }
}
