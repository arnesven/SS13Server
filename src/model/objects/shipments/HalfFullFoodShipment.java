package model.objects.shipments;

import model.items.EmptyContainer;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class HalfFullFoodShipment extends FoodShipment {

    public HalfFullFoodShipment() {
        List<GameItem> itemsToRemove = new ArrayList<>();
        for (GameItem it : this) {
            itemsToRemove.add(it);
            if (itemsToRemove.size() == 10) {
                break;
            }
        }
        this.removeAll(itemsToRemove);
        for (int i = 10; i > 0; --i) {
            this.add(new EmptyContainer());
        }
    }

    @Override
    public Shipment clone() {
        return new HalfFullFoodShipment();
    }
}
