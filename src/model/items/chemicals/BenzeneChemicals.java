package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 09/09/17.
 */
public class BenzeneChemicals extends Chemicals {
    public BenzeneChemicals() {
        super("Benzene", 31);
    }

    @Override
    public FoodItem clone() {
        return new BenzeneChemicals();
    }

    @Override
    public boolean isFlammable() {
        return true;
    }

    @Override
    public boolean isToxic() {
        return true;
    }

    @Override
    public String getFormula() {
        return "C6H6";
    }
}
