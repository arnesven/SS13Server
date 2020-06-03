package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;

/**
 * Created by erini02 on 03/12/16.
 */
public class HydrogenPeroxideChemicals extends Chemicals {
    public HydrogenPeroxideChemicals() {
        super("Hydrogen Peroxide", 25);
    }

    @Override
    public Chemicals clone() {
        return new HydrogenPeroxideChemicals();
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
    public boolean isCorrosive() {
        return true;
    }

    @Override
    public String getFormula() {
        return "H2O2";
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A colourless viscous unstable liquid with strong oxidizing properties, used in some disinfectants and bleaches.";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof EthanolChemicals) {
            return new GeneratorStartedFluid();
        }
        return super.combineWith(other);
    }
}
