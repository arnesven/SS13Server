package model.map.rooms;

import model.GameData;
import model.map.DockingPoint;
import model.map.doors.Door;
import model.objects.consoles.MiningShuttleControl;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttle extends ShuttleRoom {

    private DockingPoint initialDockingPoint;

    public MiningShuttle(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, Door[] doubles,
                         GameData gameData, DockingPoint initialDockingPoint) {
        super(id, name, x, y, w, h, ints, doubles, 2);
        this.addObject(new MiningShuttleControl(this, false));
        this.initialDockingPoint = initialDockingPoint;
    }


    @Override
    public void doSetup(GameData gameData) {
        dockYourself(gameData, this.initialDockingPoint);
    }
}
