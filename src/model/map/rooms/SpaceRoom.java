package model.map.rooms;

/**
 * Created by erini02 on 15/09/17.
 */
public class SpaceRoom extends Room {
    public SpaceRoom(int id, int x, int y, int w, int h) {
        super(id, "Deep Space", "D E E P   S P A C E", x, y, w, h, new int[]{}, new double[]{}, RoomType.space);
    }
}
