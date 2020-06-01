package model.map;

import model.map.doors.Door;
import model.map.rooms.DecorativeRoom;
import model.map.rooms.Room;
import util.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by erini02 on 23/11/16.
 */
public class Architecture {

    private final Collection<Room> allRooms;
    private final GameMap map;
    private final int zLevel;
    private final String level;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    int[][] mapMatrix;

    public Architecture(GameMap map, String level, int zLevel) {
        width = map.getMaxX() - map.getMinX();
        height = map.getMaxY() - map.getMinY();
        mapMatrix = new int[width][height];
        xOffset = map.getMinX();
        yOffset = map.getMinY();
        this.allRooms = map.getRoomsForLevel(level);
        this.map = map;
        zeroOut(map);
        this.zLevel = zLevel;
        this.level = level;

        fillInActualRooms(map, zLevel);
    }

    private void fillInActualRooms(GameMap map, int z) {
        for (Room r : allRooms) {
            for (int x = r.getX(); x < r.getX() + r.getWidth(); ++x) {
                for (int y = r.getY(); y < r.getY() + r.getHeight(); ++y) {
                    if (r.getZ() == z) {
                        setMatrix(x, y, 1);
                    }
                }
            }
        }
    }



    private void zeroOut(GameMap map) {
        for (int x = map.getMinX(); x < map.getMinX() + width; x++) {
            for (int y = map.getMinY(); y < map.getMinY() + height; y++) {
                setMatrix(x, y, 0);
            }
        }
    }

    public void checkPlacement(Room current, int width, int height, Point direction, Point2D doorPoint, Point roomPlacement) throws NoLegalPlacementForRoom {

        innerCheckPlacement(current, width, height, direction, doorPoint, roomPlacement);
      //  Logger.log(Logger.INTERESTING, "NormalDoor at " + doorPoint.getX() + ", " + doorPoint.getY());
        doorPoint.setLocation(doorPoint.getX() + 0.5*Math.abs(direction.getY()),
                             doorPoint.getY() + 0.5*Math.abs(direction.getX()));
       // Logger.log(Logger.INTERESTING, "door shifted to " + doorPoint.getX() + ", " + doorPoint.getY());


    }

    private void innerCheckPlacement(Room current, int width, int height, Point direction, Point2D doorPoint, Point roomPlacement) throws NoLegalPlacementForRoom {
//        Logger.log(Logger.CRITICAL, "Finding position for a (" +  width + "x" + height + ") room, direction " +
//                direction + " from " + current.getName() + ", a" + current.getWidth() + "x" + current.getHeight() +
//                " sized room, positioned at " + current.getX() + "," + current.getY());

        if (direction.getY() == 1 || direction.getX() == 1) {
            roomPlacement.setLocation(current.getX() + direction.getX() * current.getWidth(),
                                      current.getY() + direction.getY() * current.getHeight());
            doorPoint.setLocation(roomPlacement);
        } else {
            roomPlacement.setLocation(current.getX() + direction.getX() * width,
                                      current.getY() + direction.getY() * height);
            doorPoint.setLocation(current.getX(), current.getY());
        }

 //       Logger.log(Logger.INTERESTING, "Checking if room (" + width + "x" + height + ") can be placed at at " +
 //               roomPlacement.getX() + "," + roomPlacement.getY());

        if (isFree(roomPlacement, width, height)) {
            return;
        }
 //       Logger.log(Logger.INTERESTING, "Not OK, other room there...");

        Point shiftDirection = new Point(0, 0);
        int maxShift;
        int minShift;
        if (direction.getY() != 0) {             // HORIZONTAL SHIFT
            shiftDirection.setLocation(1, 0);
            maxShift = current.getWidth();
            minShift = width - 1;
        } else {                                 // VERTICAL SHIFT
            shiftDirection.setLocation(0, 1);
            maxShift = current.getHeight();
            minShift = height - 1;
        }

        Point originalPlacement = new Point(roomPlacement);
        Point2D originalDoor = new Point2D.Double(doorPoint.getX(), doorPoint.getY());

        // SHIFT FORWARD
        for (int shift = 0; shift < maxShift; shift++) {
            roomPlacement.setLocation(roomPlacement.getX() + shiftDirection.getX(),
                                      roomPlacement.getY() + shiftDirection.getY());
            doorPoint.setLocation(doorPoint.getX() + shiftDirection.getX(),
                                  doorPoint.getY() + shiftDirection.getY());
//            Logger.log(Logger.INTERESTING, "  Shifting (x,y)=" + shiftDirection.getX() + "," + shiftDirection.getY());
//            Logger.log(Logger.INTERESTING, "  Checking if room (" + width + "x" + height + ") at " +
//                    roomPlacement.getX() + "," + roomPlacement.getY());
            if (isFree(roomPlacement, width, height)) {
                return;
            }
   //         Logger.log(Logger.INTERESTING, "Not OK, other room there...");
        }

        roomPlacement.setLocation(originalPlacement);
        doorPoint.setLocation(originalDoor);

        // SHIFT BACKWARD
        for (int shift = 0; shift < minShift; shift++) {
            roomPlacement.setLocation(roomPlacement.getX() - shiftDirection.getX(),
                                      roomPlacement.getY() - shiftDirection.getY());

   //         Logger.log(Logger.INTERESTING, "  Shifting (x,y)=" + shiftDirection.getX() + "," + shiftDirection.getY());
    //        Logger.log(Logger.INTERESTING, "  Checking if room (" + width + "x" + height + ") at " +
    //                roomPlacement.getX() + "," + roomPlacement.getY());
            if (isFree(roomPlacement, width, height)) {
                return;
            }
     //        Logger.log(Logger.INTERESTING, "Not OK, other room there...");
        }

        throw new NoLegalPlacementForRoom();
    }

    private boolean isFree(Point roomPlacement, int width, int height) {
        for (int x = (int)roomPlacement.getX(); x < roomPlacement.getX() + width; ++x) {
            for (int y = (int)roomPlacement.getY(); y < roomPlacement.getY() + height; ++y) {
                if (getMatrix(x, y) == 1) {
     //               try {
   //                     Logger.log("Blocked by " + map.getRoomForCoordinates(x, y, zLevel, level).getName() + " at " + x + "," + y);
      //              } catch (NoSuchThingException e) {
      //                  e.printStackTrace();
       //             }
                    return false;
                }
            }
        }
        return true;
    }

    private int getMatrix(int x, int y) {
        if (x - xOffset < 0 || y - yOffset < 0) {
            return 0;
        }

        if (x - xOffset >= mapMatrix.length || y - yOffset >= mapMatrix[0].length) {
            return 0;
        }

        return mapMatrix[x - xOffset][y - yOffset];
    }


    private void setMatrix(int x, int y, int i) {
        mapMatrix[x - xOffset][y - yOffset] = i;
    }

    public Map<Room, Point2D> getPossibleNewDoors(Room position) {
        Map<Room, Point2D> map = new HashMap<>();
        Set<Point2D> currentPerimiterPoints = getPerimiterPointsForRoom(position);
        for (Room r : allRooms) {
            if (r != position && !r.isHidden() && r.getZ() == position.getZ()) {
                Set<Point2D> perimiterPoints = getPerimiterPointsForRoom(r);
                perimiterPoints.retainAll(currentPerimiterPoints);
                if (perimiterPoints.size() > 0) {
                    if (!position.getNeighborList().contains(r)) {
                        map.put(r, perimiterPoints.iterator().next());
                    }
                }
            }
        }

        return map;
    }

    private static Set<Point2D> getPerimiterPointsForRoom(Room r) {
        Set<Point2D> result = new HashSet<>();
        for (double x = r.getX()+0.5 ; x < r.getX()+r.getWidth() ; x += 0.5) {
            result.add(new Point2D.Double(x, r.getY()));
            result.add(new Point2D.Double(x, r.getY() + r.getHeight()));
        }

        for (double y = r.getY()+0.5 ; y < r.getY()+r.getHeight() ; y += 0.5) {
            result.add(new Point2D.Double(r.getX(), y));
            result.add(new Point2D.Double(r.getX() + r.getWidth(), y));
        }

        return result;
    }

    public static Point2D getDoorPositionBetween(Room position, Room selected) throws DoorNotFoundBetweenRooms {

        Set<Point2D> setA = getPerimiterPointsForRoom(position);
        Set<Point2D> setB = getPerimiterPointsForRoom(selected);
        setB.retainAll(setA);

        for (int doorIndex = 0; doorIndex < position.getDoors().length; doorIndex +=1) {
            Point2D possibleDoor = new Point2D.Double(position.getDoors()[doorIndex].getX(),
                                                      position.getDoors()[doorIndex].getY());
            if (setB.contains(possibleDoor)) {
                return possibleDoor;
            }
        }

        for (int doorIndex = 0; doorIndex < selected.getDoors().length; doorIndex +=1) {
            Point2D possibleDoor = new Point2D.Double(selected.getDoors()[doorIndex].getX(),
                                                      selected.getDoors()[doorIndex].getY());
            if (setB.contains(possibleDoor)) {
                return possibleDoor;
            }
        }

        throw new DoorNotFoundBetweenRooms();
    }

    public boolean joinRoomsWithDoor(Room a, Room b, Door newDoor) {
        Point2D pos = getPossibleNewDoors(a).get(b);
        if (pos == null) {
            Logger.log(Logger.CRITICAL, "No place to connect rooms " + a.getName() + " and " + b.getName() + " with a door!");
            return false;
        } else {
            newDoor.setPosition(pos.getX(), pos.getY());
            GameMap.addDoor(a, newDoor);
            map.joinRooms(a, b);
            return true;
        }
    }

    public List<Room> getRoomsWithin(Rectangle area) {
        List<Room> lst = new ArrayList<>();
        for (Room r : allRooms) {
            if (!r.isHidden() && !(r instanceof DecorativeRoom)) {
                if (area.intersects(getRectangleFor(r))) {
                    lst.add(r);
                }
            }
        }
        return lst;
    }

    private static Rectangle getRectangleFor(Room r) {
        return new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public boolean isAdjacentToRoom(double xDouble, double yDouble) {
        for (double dy = -1; dy < 2; ++dy) {
            for (double dx = -1; dx < 2; ++dx) {
                if (!(dx == 0 && dy == 0)) {
                    if (getMatrix((int)Math.floor(xDouble + dx), (int)Math.floor(yDouble + dy)) == 1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isOnRoom(double x, double y) {
        return getMatrix((int)x, (int)y) == 1;
    }

    public static class NoLegalPlacementForRoom extends Exception {
    }

    public static class DoorNotFoundBetweenRooms extends Throwable {
    }
}
