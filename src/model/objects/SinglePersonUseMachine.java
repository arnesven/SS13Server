package model.objects;

import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;

public abstract class SinglePersonUseMachine extends ElectricalMachinery  {

    private boolean ffVacant = true;

    public SinglePersonUseMachine(String name, Room r) {
        super(name, r);
    }

    public boolean isFancyFrameVacant() {
        return this.ffVacant;
    }

    public void setFancyFrameOccupied() {
        this.ffVacant = false;
    }


    public void setFancyFrameVacant() {
        this.ffVacant = true;
    }

}
