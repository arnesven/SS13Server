package model.map.levels;

import model.map.MapLevel;

public class AsteroidField extends MapLevel {
    public AsteroidField(String asteroid_field, String space) {
        super(asteroid_field, space);
    }

    @Override
    public boolean isJumpableTo() {
        return false;
    }
}
