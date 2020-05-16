package model.map;

import model.GameData;
import model.map.rooms.DockingPointRoom;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class MapLevel extends HashMap<String, Set<Room>> {

    private final String name;
    private String bgType;
    private GameMap mapRef;

    public MapLevel(String name, String backgroundType) {
        this.name = name;
        this.bgType = backgroundType;
    }

    public String getName() {
        return name;
    }

    public String getBackgroundType() {
        return bgType;
    }

    public List<DockingPoint> getDockingPoints() {
        List<DockingPoint> list = new ArrayList<>();
        for (Room r : mapRef.getRoomsForLevel(name)) {
            if (r instanceof DockingPointRoom) {
                for (DockingPoint dp : ((DockingPointRoom)r).getDockingPoints()) {
                    if (dp.isVacant()) {
                        list.add(dp);
                    }
                }
            }
        }
        return list;
    }

    public abstract boolean isJumpableTo();

    public void setMapRef(GameMap gameMap) {
        this.mapRef = gameMap;
    }

    public void setBackgroundType(String newBg) {
        this.bgType = newBg;
    }
}
