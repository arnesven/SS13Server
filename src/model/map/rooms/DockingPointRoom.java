package model.map.rooms;

import model.GameData;
import model.map.DockingPoint;
import model.map.doors.Door;

import java.util.List;
import java.util.Set;

public interface DockingPointRoom {

    List<DockingPoint> getDockingPoints();

    void setDocked(boolean b, GameData gameData, Set<Door> affectedDoors);
}
