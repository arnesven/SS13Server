package model.items.chemicals;

import model.items.foods.FoodItem;

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

    @Override
    public boolean isFlammable() {
        return true;
    }

    @Override
    public boolean isToxic() {
        return false;
    }

    @Override
    public String getFormula() {
        return "C2H5OH";
    }
}
