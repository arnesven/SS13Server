package model.map.rooms;

/**
 * Created by erini02 on 08/12/16.
 */
public class BrigRoom extends Room {
    public BrigRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Brig", "", x, y, w, h, ints, doubles, RoomType.security);
    }
}
