package model.modes.goals;

import model.GameData;
import model.items.general.GameItem;
import model.items.weapons.Weapon;

public class CollectThreeWeapons extends PersonalGoal {
    @Override
    public String getText() {
        return "Collect three weapons.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        int count = 0;
        for (GameItem it : getBelongsTo().getItems()) {
            if (it instanceof Weapon) {
                count++;
            }
        }
        return count >= 3;
    }
}
