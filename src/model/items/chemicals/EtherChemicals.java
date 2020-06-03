package model.items.chemicals;

import model.GameData;
import model.Player;
import model.items.foods.FoodItem;

/**
 * Created by erini02 on 03/12/16.
 */
public class EtherChemicals extends Chemicals {
    public EtherChemicals() {
        super("Ether", 26);
    }

    @Override
    public Chemicals clone() {
        return new EtherChemicals();
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
        return "(C2H5)2O";
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "a pleasant-smelling colourless volatile liquid. It is used as an anaesthetic and as a solvent or intermediate in industrial processes.";
    }
}
