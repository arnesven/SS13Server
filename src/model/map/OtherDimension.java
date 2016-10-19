package model.map;

import model.items.CosmicArtifact;

/**
 * Created by erini02 on 19/10/16.
 */
public class OtherDimension extends Room {
    public OtherDimension(int id, int[] ints, double[] doubles) {
        super(id, "Other Dimension", "", 18, 0, 0, 0,ints, doubles, RoomType.other);
        this.addItem(new CosmicArtifact());
    }
}
