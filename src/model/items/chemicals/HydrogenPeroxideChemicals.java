package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 03/12/16.
 */
public class HydrogenPeroxideChemicals extends Chemicals {
    public HydrogenPeroxideChemicals() {
        super("Hydrogen Peroxide", 25);
    }

    @Override
    public FoodItem clone() {
        return new HydrogenPeroxideChemicals();
    }
}
