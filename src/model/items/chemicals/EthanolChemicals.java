package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 03/12/16.
 */
public class EthanolChemicals extends Chemicals {
    public EthanolChemicals() {
        super("Ethanol", 23);
    }

    @Override
    public FoodItem clone() {
        return new EthanolChemicals();
    }
}
