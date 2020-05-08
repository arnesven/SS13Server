package model.map.rooms;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.ShuttleDoor;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.consoles.ShuttleControl;
import util.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttle extends ShuttleRoom {

    private DockingPoint initialDockingPoint;

    public MiningShuttle(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, Door[] doubles,
                         GameData gameData, DockingPoint initialDockingPoint) {
        super(id, name, x, y, w, h, ints, doubles, 2);
        this.addObject(new ShuttleControl(this, false));
        this.initialDockingPoint = initialDockingPoint;
    }


    @Override
    public void doSetup(GameData gameData) {
        dockYourself(gameData, this.initialDockingPoint);
    }
}
