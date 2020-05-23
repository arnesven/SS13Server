package model.map.levels;

import model.map.MapLevel;

public class StationLevel extends MapLevel {
    public StationLevel(String firstLevelName, String space) {
        super(firstLevelName, space);
    }

    @Override
    public boolean isJumpableTo() {
        return false;
    }
}
