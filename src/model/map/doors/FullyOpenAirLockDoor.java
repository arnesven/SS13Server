package model.map.doors;


public class FullyOpenAirLockDoor extends AirLockDoor {
    public FullyOpenAirLockDoor(double x, double y, double z, int fromId, int toId) {
        super(x, y, z, fromId, toId, true);
    }

}
