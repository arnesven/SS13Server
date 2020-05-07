package model.objects.recycling;

import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

public class RecyclingContainer extends CrateObject {
    public RecyclingContainer(Room r) {
        super(r, "Recycling Container");
    }
}
