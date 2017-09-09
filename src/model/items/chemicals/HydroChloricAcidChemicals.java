package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 09/09/17.
 */
public class HydroChloricAcidChemicals extends Chemicals {
    public HydroChloricAcidChemicals() {
        super("Hydrocloric Acid", 24);
    }

    @Override
    public FoodItem clone() {
        return new HydroChloricAcidChemicals();
    }

    @Override
    public boolean isFlammable() {
        return false;
    }

    @Override
    public boolean isToxic() {
        return true;
    }

    @Override
    public String getFormula() {
        return "HCl";
    }
}
