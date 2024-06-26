package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import model.objects.general.Antidote;

/**
 * Created by erini02 on 09/09/17.
 */
public class BenzeneChemicals extends Chemicals {
    public BenzeneChemicals() {
        super("Benzene", 31);
    }

    @Override
    public Chemicals clone() {
        return new BenzeneChemicals();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A liquid with an aromatic scent.";
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
        return "C6H6";
    }

    @Override
    public GameItem combineWith(Chemicals other) {
        if (other instanceof AcetoneChemicals || other instanceof EtherChemicals || other instanceof EthanolChemicals) {
            return new Antidote();
        }

        return super.combineWith(other);
    }
}
