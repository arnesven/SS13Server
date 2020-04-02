package model.map.rooms;

import model.map.doors.UpgoingStairsDoor;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.general.JunkVendingMachine;

public class LoungeRoom extends SupportRoom {

    public LoungeRoom(int ID, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, "Lounge", "", x, y, width, height, neighbors, doors);
        setZ(-1);
        addObject(new UpgoingStairsDoor(this));
        addObject(new JunkVendingMachine(this));
    }

    @Override
    protected FloorSet getFloorSet() {
        return new ChapelFloorSet();
    }
}