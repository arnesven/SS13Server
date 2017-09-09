package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 09/09/17.
 */
public class SulfuricAcidChemicals extends Chemicals {
    public SulfuricAcidChemicals() {
        super("Sulfuric Acid", 27);
    }

    @Override
    public FoodItem clone() {
        return new SulfuricAcidChemicals();
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
        return "H2SO4";
    }
}
