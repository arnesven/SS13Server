package model.modes.goals;

import model.GameData;
import model.items.suits.SuitItem;

/**
 * Created by erini02 on 05/12/16.
 */
public class LayerUponLayerGoal extends PersonalGoal {
    private final int quant;

    public LayerUponLayerGoal(int i) {
        this.quant = i;
    }

    @Override
    public String getText() {
        return "Wear " + quant + " wearable items.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return SuitItem.countSuits(getBelongsTo()) >= quant;
    }
}
