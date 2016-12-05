package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.foods.Alcohol;
import model.items.foods.FoodItem;

/**
 * Created by erini02 on 05/12/16.
 */
public class DrinkAlcoholGoal extends PersonalGoal {
    private final int quant;
    private int drunk = 0;

    public DrinkAlcoholGoal(int i) {
        this.quant = i;
    }

    @Override
    public String getText() {
        return "Drink " + quant + " alcoholic beverages.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return drunk >= quant;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new DrinkAlcoholCheckerDeckorator(belongingTo.getCharacter()));
    }

    private class DrinkAlcoholCheckerDeckorator extends CharacterDecorator {
        public DrinkAlcoholCheckerDeckorator(GameCharacter character) {
            super(character, "drinkalcoholchecker");
        }

        @Override
        public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {
            super.doWhenConsumeItem(foodItem, gameData);
            if (foodItem instanceof Alcohol) {
                drunk += 1;
            }
        }
    }
}
