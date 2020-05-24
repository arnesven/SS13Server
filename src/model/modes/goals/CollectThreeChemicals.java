package model.modes.goals;

import model.GameData;
import model.items.chemicals.Chemicals;
import model.items.general.GameItem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 04/12/16.
 */
public class CollectThreeChemicals extends PersonalGoal {
    @Override
    public String getText() {
        return "Collect three different types of chemicals";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        Set<String> chemSet = new HashSet<>();
        for (GameItem it : getBelongsTo().getItems()) {
            if (it instanceof Chemicals) {
                chemSet.add(it.getPublicName(getBelongsTo()));
            }
        }
        return chemSet.size() >= 3;
    }
}
