package model.objects.shipments;

import model.items.general.RobotParts;

/**
 * Created by erini02 on 14/04/16.
 */
public class RobotPartsShipment extends Shipment {
    public RobotPartsShipment() {
        super("Robot Parts");
        this.add(new RobotParts());
        this.add(new RobotParts());
    }

    @Override
    public Shipment clone() {
        return new RobotPartsShipment();
    }
}
