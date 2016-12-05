package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.foods.FoodItem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 05/12/16.
 */
public class EatDifferentFoods extends PersonalGoal {
    private final int quant;
    private Set<String> consumeSet = new HashSet<>();

    public EatDifferentFoods(int i) {
        this.quant = i;
    }

    @Override
    public String getText() {
        return "Consume " + quant + " different consumable items.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CountDifferentConsumesDecorator(belongingTo.getCharacter()));
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return consumeSet.size() >= quant;
    }

    private class CountDifferentConsumesDecorator extends CharacterDecorator {
        public CountDifferentConsumesDecorator(GameCharacter character) {
            super(character, "consumecounter");
        }

        @Override
        public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {
            super.doWhenConsumeItem(foodItem, gameData);
            consumeSet.add(foodItem.getPublicName(getActor()));
        }
    }
}
