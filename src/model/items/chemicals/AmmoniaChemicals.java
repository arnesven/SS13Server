package model.items.chemicals;

import model.items.foods.FoodItem;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.PoisonSyringe;

/**
 * Created by erini02 on 09/09/17.
 */
public class AmmoniaChemicals extends Chemicals {
    public AmmoniaChemicals() {
        super("Ammonia", 30);
    }

    @Override
    public FoodItem clone() {
        return new AmmoniaChemicals();
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
        return "NH3";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof AcetoneChemicals) {
            return new PoisonSyringe();
        }

        return super.combineWith(other);
    }
}
