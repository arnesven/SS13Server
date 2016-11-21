package model.objects.shipments;

import model.items.general.ElectronicParts;
import model.items.general.RoomPartsStack;

/**
 * Created by erini02 on 20/11/16.
 */
public class ConstructionShipment extends Shipment {
    public ConstructionShipment() {
        super("Construction");
        this.add(new RoomPartsStack(3));
        this.add(new ElectronicParts());
        this.add(new ElectronicParts());
        this.add(new ElectronicParts());
    }

    @Override
    public Shipment clone() {
        return new ConstructionShipment();
    }
}
