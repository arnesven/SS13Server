package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;

/**
 * Created by erini02 on 09/09/17.
 */
public class AcetoneChemicals extends Chemicals {
    public AcetoneChemicals() {
        super("Acetone", 29);
    }

    @Override
    public Chemicals clone() {
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
    public boolean isCorrosive() {
        return false;
    }

    @Override
    public String getFormula() {
        return "(CH3)2CO";
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A colourless volatile liquid ketone made by oxidizing isopropanol, used as an organic solvent and synthetic reagent.";
    }
}
