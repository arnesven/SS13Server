package model.objects.consoles;

import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.power.PowerSupply;

public class SolarArrayControl extends GameObject implements PowerSupply {
    private final int solarPanelRoomID;

    public SolarArrayControl(Room r, int solarPanelRoomID) {
        super("Solar Array Control #" + solarPanelRoomID, r);
        this.solarPanelRoomID = solarPanelRoomID;
    }

    @Override
    public String getName() {
        return "Solar Array #" + solarPanelRoomID;
    }

    @Override
    public double getPower() {
        return 0;
    }

    @Override
    public double getEnergy() {
        return 0;
    }

    @Override
    public void drainEnergy(double d) {

    }
}
