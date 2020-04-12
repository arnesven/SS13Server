package model.objects.shipments;

import model.items.foods.RawFoodContainer;

public class FoodShipment extends Shipment {
    public FoodShipment() {
        super("Food Crate");
        for (int i = 0; i < 20; ++i) {
            this.add(new RawFoodContainer());
        }
    }

    @Override
    public Shipment clone() {
        return new FoodShipment();
    }
}
