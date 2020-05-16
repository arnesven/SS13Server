package model.map.levels;

import model.map.MapLevel;

public class EmptySpaceLevel extends MapLevel {
    public EmptySpaceLevel(String name, String backgroundType) {
        super(name, backgroundType);
    }

    @Override
    public boolean isJumpableTo() {
        return true;
    }
}
