package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 09/09/17.
 */
public class AcetoneChemicals extends Chemicals {
    public AcetoneChemicals() {
        super("Acetone", 29);
    }

    @Override
    public FoodItem clone() {
        return new AcetoneChemicals();
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
        return "(CH3)2CO";
    }
}
