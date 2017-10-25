package model.map.rooms;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends Room {
    public OtherDimension(int id, int[] ints, double[] doubles) {
        super(id, "Other Dimension", "", 0, 0, 10, 10,ints, doubles, RoomType.outer);

    }
}
