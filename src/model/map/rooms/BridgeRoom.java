package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.items.suits.SpaceSuit;
import model.objects.consoles.AirLockControl;
import model.objects.consoles.FTLControl;

/**
 * Created by erini02 on 15/12/16.
 */
public class BridgeRoom extends Room {
    public BridgeRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType command) {
        super(i, "Bridge", "Brdg", i1, i2, i3, i4, ints, doubles, command);
        addItem(new SpaceSuit());
        addItem(new FireExtinguisher());
        addObject(new AirLockControl(this));
        addObject(new FTLControl(this));

    }
}
