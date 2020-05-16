package model.map.levels;

import model.map.MapLevel;
import model.map.rooms.OrbitalPlanetRoom;
import model.map.rooms.PlanetRoom;

public class PlanetSystem extends MapLevel {
    public PlanetSystem(String prison_planet, OrbitalPlanetRoom planet) {
        super(prison_planet, "Orbit" + planet.getOrbitSprite().getColumn() + ";" +
                planet.getOrbitSprite().getRow() + ";" + planet.getOrbitSprite().getWidth());
    }

    @Override
    public boolean isJumpableTo() {
        return true;
    }
}
