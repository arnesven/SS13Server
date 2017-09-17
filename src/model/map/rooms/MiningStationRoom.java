package model.map.rooms;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningStationRoom extends Room {
    public static final int MS_WIDTH = 2;
    public static final int MS_HEIGHT = 2;
    public static final int DEFAULT_ID = 555;

    public MiningStationRoom(int x, int y) {
        super(DEFAULT_ID, "Mining Station", "MS", x, y, MS_WIDTH, MS_HEIGHT, new int[]{}, new double[]{}, RoomType.tech);
    }
}
