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
public class ShuttleRoom extends Room {

    private DockingPoint initialDockingPoint;
    private DockingPoint dockedAtPoint;
    private Door dockedDoor;
    private int oldDoorToID;
    private String direction = "right";
    private Map<String, String> nextDirection;
    private Map<String, Set<Point>> forbiddenDockingDirections;

    public ShuttleRoom(int id, String name, String shortname, int x, int y, int w, int h, int[] ints, Door[] doubles,
                       GameData gameData, DockingPoint initialDockingPoint) {
        super(id, name, x, y, w, h, ints, doubles);
        this.addObject(new ShuttleControl(this));
        this.initialDockingPoint = initialDockingPoint;
        Map<String, String> newMap = Map.of("right", "up", "up", "left", "left", "down", "down", "right");
        nextDirection = newMap;
        forbiddenDockingDirections = Map.of("right", Set.of(new Point(1, 0)), "up", Set.of(new Point(0, -1)),
                "left", Set.of(new Point(-1, 0)), "down", Set.of(new Point(0, 1)));
    }

    @Override
    protected String getWallAppearence() {
        return "shuttle" + direction;
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
        String initialRotation = direction;
        boolean result;
        result = getDockingPosition(gameData, dp, roomPlacement, doorPlacement);
        while (!initialRotation.equals(direction)) { // rotate back.
            this.rotate();
        }
        return result;
    }

    private boolean getDockingPosition(GameData gameData, DockingPoint dp, Point roomPlacement, Point2D doorPlacement) {
       // Logger.log("Smurfing for " + dp.getName());
        for (int i = 0; i < 4; ++i) {
           // Logger.log(" ..." + direction);
            if (!forbiddenDockingDirections.get(direction).contains(dp.getDirection())) {
                try {
                    Architecture arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(dp.getRoom()).getName(),
                            dp.getRoom().getZ());
                    arc.checkPlacement(dp.getRoom(), getWidth(), getHeight(), dp.getDirection(), doorPlacement, roomPlacement);
                    return true;
                } catch (Architecture.NoLegalPlacementForRoom nlpfe) {
         //           Logger.log(" ... blocked");
                    // OK, try next rotation
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                    break;
                }
            } else {
           //     Logger.log("... forbidden");
            }
            this.rotate();
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



    private void rotate() {
        int width = getWidth();
        setWidth(getHeight());
        setHeight(width);
        direction = nextDirection.get(direction);
    }

    public Room getDockingPointRoom() {
        return dockedAtPoint.getRoom();
    }


    @Override
    public void doSetup(GameData gameData) {
        dockYourself(gameData, this.initialDockingPoint);
    }
}
