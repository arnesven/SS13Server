package model.map.rooms;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.*;
import model.characters.general.GameCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.DockingPoint;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.StayBehavior;
import model.objects.decorations.ShuttleChair;
import model.objects.decorations.ShuttleDecoration;
import model.objects.decorations.ShuttleThruster;
import model.objects.power.LifeSupport;
import model.objects.power.Lighting;
import util.HTMLText;
import util.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShuttleRoom extends Room {

    private DockingPoint dockedAtPoint;
    private Door dockedDoor;
    private int oldDoorToID;
    private String direction = "right";
    private Map<String, String> nextDirection;
    private Map<String, Set<Point>> forbiddenDockingDirections;
    private List<ShuttleDecoration> decorations;

    public ShuttleRoom(int ID, String name, int x, int y, int width, int height, int[] neighbors, Door[] doors, int numberOfChairs) {
        super(ID, name, x, y, width, height, neighbors, doors);
        Map<String, String> newMap = Map.of("right", "up", "up", "left", "left", "down", "down", "right");
        nextDirection = newMap;
        forbiddenDockingDirections = Map.of("right", Set.of(new Point(1, 0), new Point(-1, 0)),
                "up", Set.of(new Point(0, -1), new Point(0, 1)),
                "left", Set.of(new Point(-1, 0), new Point(1, 0)),
                "down", Set.of(new Point(0, 1), new Point(0, -1)));
        decorations = new ArrayList<>();
        addDecoration(new ShuttleThruster(this));
        for (int i = numberOfChairs; i > 0; --i) {
            addDecoration(new ShuttleChair(this));
        }
        moveTo(x, y, getZ());
    }

    protected void addDecoration(ShuttleDecoration decor) {
        Logger.log("Added decoration " + decor.getBaseName() + " to room " + getName());
        addObject(decor);
        decorations.add(decor);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }

    @Override
    public LifeSupport getLifeSupport() {
        return null;
    }

    @Override
    public Lighting getLighting() {
        return null;
    }

    @Override
    public void setZ(int i) {
        super.setZ(i);
        for (ShuttleDecoration decor : decorations) {
            if (decor.hasAbsolutePosition()) {
                decor.setAbsolutePosition(decor.getAbsoluteX(), decor.getAbsoluteY(), i);
            }
        }
    }

    @Override
    public List<Sprite> getAlwaysSprites(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        for (ShuttleDecoration decor : decorations) {
            if (decor.alwaysShowSprite()) {
                list.add(decor.getSprite(whosAsking));
            }
        }
        return list;
    }

    @Override
    protected String getWallAppearence() {
        return "shuttle" + direction;
    }

    public void moveTo(int x, int y, int z) {
        setCoordinates(x, y, z);
        for (ShuttleDecoration decor : decorations) {
            if (decor.hasAbsolutePosition()) {
                decor.moveTo(x, y, z);
            }
        }
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
        for (int i = 0; i < 4; ++i) {
            if (!forbiddenDockingDirections.get(direction).contains(dp.getDirection())) {
                try {
                    Architecture arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(dp.getRoom()).getName(),
                            dp.getRoom().getZ());
                    arc.checkPlacement(dp.getRoom(), getWidth(), getHeight(), dp.getDirection(), doorPlacement, roomPlacement);
                    return true;
                } catch (Architecture.NoLegalPlacementForRoom nlpfe) {
                    // OK, try next rotation
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                    break;
                }
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

    public void undockYourself(GameData gameData) {
        gameData.getMap().separateRooms(this, (Room)dockedAtPoint.getRoom());
        dockedAtPoint.setDocked(gameData, false);
        dockedAtPoint = null;
        if (dockedDoor != null) {
            dockedDoor.setToId(oldDoorToID);
        }
    }



    public void rotate() {
        int width = getWidth();
        setWidth(getHeight());
        setHeight(width);
        direction = nextDirection.get(direction);
    }

    public Room getDockingPointRoom() {
        return dockedAtPoint.getRoom();
    }

    public String getDirection() {
        return direction;
    }

    public boolean isDocked() {
        return dockedAtPoint != null;
    }

    public void bootActors() {
        Room bootRoom;
        if (getDockingPointRoom().getNeighborList().isEmpty()) {
            bootRoom = getDockingPointRoom();
        } else {
            bootRoom  =getDockingPointRoom().getNeighborList().get(0);
        }

        for (Actor a : getActors()) {
            a.setPosition(bootRoom);
            a.addTolastTurnInfo("The " + getName().toLowerCase() + " left, you were moved to the " + bootRoom.getName() + ".");
        }
    }


}
