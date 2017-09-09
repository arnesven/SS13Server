package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;

/**
 * Created by erini02 on 09/09/17.
 */
public class SodiumChloride extends Chemicals {
    public SodiumChloride() {
        super("Sodium Chloride", 28);
    }

    @Override
    public FoodItem clone() {
        return new SodiumChloride();
    }

    @Override
    public boolean isFlammable() {
        return false;
    }

    @Override
    public boolean isToxic() {
        return false;
    }

    @Override
    public String getFormula() {
        return "NaCl";
    }
}
