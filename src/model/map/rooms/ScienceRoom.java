package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.ScienceFloorSet;
import sounds.Sound;

public abstract class ScienceRoom extends StationRoom {

    public ScienceRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new ScienceFloorSet();
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambinice");
    }
}
