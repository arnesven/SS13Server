package model.modes.goals;

import model.GameData;
import model.items.general.GameItem;

/**
 * Created by erini02 on 15/09/17.
 */
public class HaveItemGoal extends PersonalGoal {

    private final GameItem target;

    public HaveItemGoal(GameItem target) {
        this.target = target;
    }

    @Override
    public String getText() {
        return "Have a " + target.getBaseName() + " in your inventory at the end of the game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return GameItem.hasAnItemOfClass(getBelongsTo(), target.getClass());
    }
}
