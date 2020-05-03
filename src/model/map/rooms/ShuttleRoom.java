package model.map.rooms;

import model.Actor;
import model.GameData;
import model.Player;
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

/**
 * Created by erini02 on 17/09/17.
 */
public class ShuttleRoom extends Room {

    private DockingPoint initialDockingPoint;
    private DockingPoint dockedAtPoint;
    private Door dockedDoor;
    private int oldDoorToID;

    public ShuttleRoom(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, Door[] doubles,
                       GameData gameData, DockingPoint initialDockingPoint) {
        super(id, name, x, y, w, h, ints, doubles);
        this.addObject(new ShuttleControl(this));
        this.initialDockingPoint = initialDockingPoint;
    }

    public void moveTo(int x, int y, int z) {
        setCoordinates(x, y, z);
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("shuttlefloor", 19, 28);
    }

    public boolean canDockAt(GameData gameData, DockingPoint dp) {
        Point2D doorPlacement = new Point2D.Double();
        Point roomPlacement = new Point();
        try {
            try {
                Architecture arc = new Architecture(gameData.getMap(),
                        gameData.getMap().getLevelForRoom(this).getName(), ((Room)dp.getRoom()).getZ());
                arc.checkPlacement((Room)dp.getRoom(), getWidth(), getHeight(), dp.getDirection(), doorPlacement, roomPlacement);
                return true;
            } catch (Architecture.NoLegalPlacementForRoom nlpfe) {
                Architecture arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(this).getName(),
                        ((Room)dp.getRoom()).getZ());
                arc.checkPlacement((Room)dp.getRoom(), getHeight(), getWidth(), dp.getDirection(), doorPlacement, roomPlacement);
                return true;
            }
        } catch (Architecture.NoLegalPlacementForRoom nlpfe) {

        } catch (NoSuchThingException nste) {

        }

        return false;
    }

    public void dockYourself(GameData gameData, DockingPoint selectedDockingPoint) {
        Point roomPos = new Point();
        Point2D.Double doorPos = new Point2D.Double();
        getDockingPosition(gameData, selectedDockingPoint, roomPos, doorPos);
        Logger.log("Docking shuttle at " + roomPos);
        moveTo((int)roomPos.getX(), (int)roomPos.getY(), ((Room)selectedDockingPoint.getRoom()).getZ());
        gameData.getMap().joinRooms(this, (Room)selectedDockingPoint.getRoom());
        selectedDockingPoint.setDocked(gameData, true);
        this.dockedAtPoint = selectedDockingPoint;
        Room r = (Room)selectedDockingPoint.getRoom();
        for (Door d : r.getDoors()) {
            if (d.getX() == doorPos.getX() && d.getY() == doorPos.getY()) {
                this.oldDoorToID = d.getToId();
                d.setToId(this.getID());
                this.dockedDoor = d;
                break;
            }
        }
    }

    public void undockYourself(GameData gameData, Actor performingClient) {
        gameData.getMap().separateRooms(this, (Room)dockedAtPoint.getRoom());
        dockedAtPoint.setDocked(gameData, false);
        dockedAtPoint = null;
        if (dockedDoor != null) {
            dockedDoor.setToId(oldDoorToID);
        }
    }


    private void getDockingPosition(GameData gameData, DockingPoint dp, Point roomPlacement, Point2D.Double doorPlacement) {
        try {
            try {
                Architecture arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(this).getName(),
                        ((Room)dp.getRoom()).getZ());
                arc.checkPlacement((Room)dp.getRoom(), getWidth(), getHeight(), dp.getDirection(), doorPlacement, roomPlacement);
            } catch (Architecture.NoLegalPlacementForRoom nlpfe) {
                Architecture arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(this).getName(),
                        ((Room)dp.getRoom()).getZ());
                arc.checkPlacement((Room)dp.getRoom(), getHeight(), getWidth(), dp.getDirection(), doorPlacement, roomPlacement);
                this.rotate();
            }
        } catch (Architecture.NoLegalPlacementForRoom nlpfe) {

        } catch (NoSuchThingException nste) {

        }

    }

    private void rotate() {
        int width = getWidth();
        setWidth(getHeight());
        setHeight(width);
    }

    public Room getDockingPointRoom() {
        return (Room)dockedAtPoint.getRoom();
    }


    @Override
    public void doSetup(GameData gameData) {
        dockYourself(gameData, this.initialDockingPoint);
    }
}
