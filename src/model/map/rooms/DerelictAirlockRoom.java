package model.map.rooms;

import model.map.doors.Door;

public class DerelictAirlockRoom extends AirLockRoom {
    public DerelictAirlockRoom(int i, String name, String s, int i1, int i2, int i3, int i4, int[] ints, Door[] doors) {
        super(i, 99, i1, i2, i3, i4, ints, doors);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }
}
