package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;

/**
 * Created by erini02 on 03/12/16.
 */
public class EthanolChemicals extends Chemicals {
    public EthanolChemicals() {
        super("Ethanol", 23);
    }

    @Override
    public Chemicals clone() {
        return new EthanolChemicals();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Common alcohol, albeit very concentrated.";
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
    public boolean isCorrosive() {
        return false;
    }

    @Override
    public String getFormula() {
        return "C2H5OH";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof HydrogenPeroxideChemicals) {
            return new GeneratorStartedFluid();
        }
        return super.combineWith(other);
    }
}
