package model.objects.shipments;

import model.items.general.*;
import model.items.suits.InsulatedGloves;
import model.items.tools.CraftingTools;
import model.items.tools.RepairTools;

/**
 * Created by erini02 on 02/05/16.
 */
public class TechnicalShipment extends Shipment {
    public TechnicalShipment() {
        super("Technical");
        this.add(new CraftingTools());
        this.add(new CraftingTools());
        this.add(new RepairTools());
        this.add(new RepairTools());
        this.add(new PowerRadio());
        this.add(new Multimeter());
        this.add(new InsulatedGloves());
        this.add(new Laptop());
    }

    @Override
    public Shipment clone() {
        return new TechnicalShipment();
    }
}
