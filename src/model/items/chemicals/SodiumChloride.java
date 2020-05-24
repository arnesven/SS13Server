package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import model.objects.general.Antidote;

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

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof AmmoniaChemicals) {
            return new Antidote();
        }

        return super.combineWith(other);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A liquid chemical compound, in a small container.";
    }
}
