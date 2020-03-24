package model.map.rooms;

import model.objects.OperativeStorage;

public class NukieShipStorageRoom extends NukieShipRoom {
    public NukieShipStorageRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles) {
        super(i, i1, i2, i3, i4, ints, doubles);
        setName("Storage Room");
        OperativeStorage storage = new OperativeStorage(this);
        this.addObject(storage);
    }
}
