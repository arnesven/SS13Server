package model.modes.goals;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.foods.CustomFoodItem;
import model.objects.general.CookOMatic;
import util.Logger;

public class MakeCustomDishGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Design at least one custom dish during this game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {

        try {
            CookOMatic cooker = gameData.findObjectOfType(CookOMatic.class);
            for (CustomFoodItem cfi : cooker.getCustomDishDesigner().getSavedDishes()) {
                if (cfi.getDesigner() == getBelongsTo()) {
                    return true;
                }
            }
        } catch (NoSuchThingException e) {
            Logger.log("Could not find Cook-O-Matic, cannot verify goal completion...");
            e.printStackTrace();
        }


        return false;
    }
}
