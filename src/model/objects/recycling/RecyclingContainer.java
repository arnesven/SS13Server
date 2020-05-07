package model.objects.recycling;

import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

public class RecyclingContainer extends CrateObject {
    private static final int CONTAINER_MAX_SIZE = 12;

    public RecyclingContainer(Room r) {
        super(r, "Recycling Container");
    }

    public boolean isFull() {
        return getInventory().size() >= CONTAINER_MAX_SIZE;
    }
}
