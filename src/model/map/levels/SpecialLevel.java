package model.map.levels;

import model.map.MapLevel;

public class SpecialLevel extends MapLevel {
    public SpecialLevel(String other_dimension, String planet) {
        super(other_dimension, planet);
    }

    @Override
    public boolean isJumpableTo() {
        return false;
    }
}
