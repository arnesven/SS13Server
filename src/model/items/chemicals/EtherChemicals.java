package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 03/12/16.
 */
public class EtherChemicals extends Chemicals {
    public EtherChemicals() {
        super("Ether", 26);
    }

    @Override
    public FoodItem clone() {
        return new EtherChemicals();
    }
}
