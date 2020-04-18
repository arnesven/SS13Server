package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.items.suits.SpaceSuit;
import model.map.doors.Door;
import model.objects.consoles.AirLockControl;
import model.objects.consoles.FTLControl;
import model.objects.consoles.ShuttleControl;

/**
 * Created by erini02 on 15/12/16.
 */
public class BridgeRoom extends CommandRoom {
    public BridgeRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Bridge", "Brdg", x, y, w, h, ints, doubles);
        addItem(new SpaceSuit());
        addItem(new FireExtinguisher());
        addObject(new AirLockControl(this));
        addObject(new FTLControl(this));
        addObject(new ShuttleControl(this));

    }
}
