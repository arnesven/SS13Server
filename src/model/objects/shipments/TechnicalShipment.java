package model.objects.shipments;

import model.items.general.*;

/**
 * Created by erini02 on 02/05/16.
 */
public class TechnicalShipment extends Shipment {
    public TechnicalShipment() {
        super("Technical", 6000);
        this.add(new Tools());
        this.add(new Tools());
        this.add(new PowerRadio());
        this.add(new GeigerMeter());
        this.add(new Laptop());
    }

    @Override
    public Shipment clone() {
        return new TechnicalShipment();
    }
}