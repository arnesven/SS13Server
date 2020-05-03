package model.map;

import model.map.rooms.DockingPointRoom;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MapLevel extends HashMap<String, Set<Room>> {

    private final String name;
    private final String bgType;
    private final GameMap mapRef;

    public MapLevel(String name, String backgroundType, GameMap mapRef) {
        this.name = name;
        this.bgType = backgroundType;
        this.mapRef = mapRef;
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
}
