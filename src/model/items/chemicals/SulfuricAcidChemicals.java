package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import model.items.general.PoisonSyringe;

/**
 * Created by erini02 on 09/09/17.
 */
public class SulfuricAcidChemicals extends Chemicals {
    public SulfuricAcidChemicals() {
        super("Sulfuric Acid", 27);
    }

    @Override
    public Chemicals clone() {
        return new SulfuricAcidChemicals();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An abrasive liquid which destroys anything it is poured on.";
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
        return "H2SO4";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof EtherChemicals) {
            return new PoisonSyringe();
        }

        return super.combineWith(other);
    }
}
