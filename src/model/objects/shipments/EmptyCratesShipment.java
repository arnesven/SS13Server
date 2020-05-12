package model.objects.shipments;

import model.GameData;
import model.map.rooms.Room;
import model.objects.general.CrateObject;

public class EmptyCratesShipment extends Shipment {
    public EmptyCratesShipment() {
        super("Empty Crates");
    }

    @Override
    public Shipment clone() {
        return new EmptyCratesShipment();
    }

    @Override
    public int getCost() {
        return 100;
    }

    @Override
    public void hasArrivedIn(Room position, GameData gameData) {
        for (int i = 9; i > 0; --i) {
            position.addObject(new CrateObject(position, "Crate"));
        }
    }
}
