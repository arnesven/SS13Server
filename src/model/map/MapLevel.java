package model.map;

import model.map.rooms.Room;

import java.util.HashMap;
import java.util.Set;

public class MapLevel extends HashMap<String, Set<Room>> {

    private final String name;
    private final String bgType;

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
}
