package model.map.rooms;

import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.objects.mining.GeneralManufacturer;
import model.objects.mining.MiningStorage;
import model.objects.power.Battery;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningStationRoom extends TechRoom {
    public static final int MS_WIDTH = 2;
    public static final int MS_HEIGHT = 2;
    public static final int DEFAULT_ID = 555;

    public MiningStationRoom(int x, int y) {
        super(DEFAULT_ID, "Mining Station", "MS", x, y, MS_WIDTH, MS_HEIGHT, new int[]{556}, new Door[]{});
        this.addObject(new MiningStorage(this));
        this.addObject(new GeneralManufacturer(this));
        this.addObject(new Battery(this, Battery.MAX_ENERGY, false));
        this.addObject(new Battery(this, Battery.MAX_ENERGY, false));
    }

    @Override
    public FloorSet getFloorSet() {
        return new FloorSet("miningfloor",  24, 10);
    }
}
