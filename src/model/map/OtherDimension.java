package model.map;

import model.items.CosmicArtifact;
import model.objects.general.DimensionPortal;
import util.MyRandom;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends Room {
    public OtherDimension(int id, int[] ints, double[] doubles) {
        super(id, "Other Dimension", "", 0, 0, 1, 1,ints, doubles, RoomType.outer);

    }
}
