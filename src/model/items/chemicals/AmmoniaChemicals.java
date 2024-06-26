package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import model.items.general.PoisonSyringe;
import model.objects.general.Antidote;

/**
 * Created by erini02 on 09/09/17.
 */
public class AmmoniaChemicals extends Chemicals {
    public AmmoniaChemicals() {
        super("Ammonia", 30);
    }

    @Override
    public Chemicals clone() {
        return new AmmoniaChemicals();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A clear liquid with a powerful smell.";
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
        return "NH3";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof AcetoneChemicals) {
            return new PoisonSyringe();
        }
        if (other instanceof SodiumChloride) {
            return new Antidote();
        }

        return super.combineWith(other);
    }
}
