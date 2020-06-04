package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;

public class CustomRoom extends StationRoom {
    private final FloorSet floorSet;

    public CustomRoom(int id, String roomName, String s, int roomX, int roomY, int roomZ, int width, int height, int[] ints, Door[] doors, String floors) {
        super(id, roomName, roomX, roomY, width, height, ints, doors);
        setZ(roomZ);
        setFloorSet(FloorSet.getBuildableFloorSets().get(floors));
        this.floorSet = getFloorSet();
    }

    @Override
    public FloorSet getFloorSet() {
        return floorSet;
    }
}
