package model.modes.goals;

import model.GameData;
import model.items.general.GameItem;
import model.items.seeds.MushroomSpores;
import model.objects.plants.Mushroom;
import model.objects.plants.MushroomItem;

/**
 * Created by erini02 on 15/09/17.
 */
public class HaveAMushroom extends PersonalGoal {

    @Override
    public String getText() {
        return "Have a Mushroom in your inventory at the end of the game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return GameItem.hasAnItemOfClass(getBelongsTo(), MushroomItem.class);
    }
}
