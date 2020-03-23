package model.map.rooms;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends RemoteRoom {
    public OtherDimension(int id, int[] ints, double[] doubles) {
        super(id, "Other Dimension", "", 0, 0, 10, 10,ints, doubles);
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("otherdimension", 13, 17);
    }
}
