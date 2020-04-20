package model.map.doors;


public class FullyOpenAirLockDoor extends AirLockDoor {
    public FullyOpenAirLockDoor(double x, double y, int fromId, int toId) {
        super(x, y, fromId, toId, true);
    }

}
