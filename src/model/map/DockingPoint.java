package model.map;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.ActionOption;
import model.map.doors.Door;
import model.map.rooms.DockingPointRoom;
import model.map.rooms.Room;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class DockingPoint implements Serializable {

    private final String name;
    private final Point toDir;
    private final Point fromDir;
    private final DockingPointRoom roomRef;
    private final String description;
    private boolean isDocked = false;

    public DockingPoint(String name, String description, Point fromDir, Point toDir, DockingPointRoom room) {
        this.name = name;
        this.toDir = toDir;
        this.fromDir = fromDir;
        this.roomRef = room;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Point getDirection() {
        return toDir;
    }

    public DockingPointRoom getRoom() {
        return roomRef;
    }

    public void setDocked(GameData gameData, boolean b) {
        this.isDocked = b;
        Set<Door> affected = new HashSet<>();

        Room r = (Room)roomRef;
        for (Door d : r.getDoors()) {
            if (dirCheck(fromDir, d, r)) {
                affected.add(d);
            }

            if (dirCheck(toDir, d, r)) {
                affected.add(d);
            }
        }

        roomRef.setDocked(b, gameData, affected);
    }

    private boolean dirCheck(Point dir, Door d, Room r) {
        if (dir.getY() == 0) {
            if (d.getX() == r.getX() + dir.getX()*0.5 + 0.5) {
                return true;
            }
        } else if (dir.getX() == 0) {
            if (d.getY() == r.getY() + dir.getY()*0.5 + 0.5) {
                return true;
            }
        }
        return false;
    }

    public boolean isVacant() {
        return !isDocked;
    }
}
