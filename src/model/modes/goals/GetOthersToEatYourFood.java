package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.itemactions.ConsumeAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.foods.FoodItem;
import model.items.general.GameItem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 08/12/16.
 */
public class GetOthersToEatYourFood extends PersonalGoal {
    private final int amount;
    private int current;

    public GetOthersToEatYourFood(int i) {
        this.amount = i;
        this.current = 0;
    }

    @Override
    public String getText() {
        return "Get others to eat " + amount + " of your cooked food.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new FoodEatenByOthersChecker(belongingTo.getCharacter()));
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return current >= amount;
    }

    private class FoodEatenByOthersChecker extends CharacterDecorator {
        private final Set<FoodItem> trackedFood;

        public FoodEatenByOthersChecker(GameCharacter character) {
            super(character, "foodeatenbyotherschecker");
            this.trackedFood = new HashSet<FoodItem>();
        }

        @Override
        public void giveItem(GameItem it, Target giver) {
            super.giveItem(it, giver);
            for (GameItem it2 : getItems()) {
                if (it2 instanceof FoodItem) {
                    if (!trackedFood.contains(it2)) {
                        trackedFood.add((FoodItem) it2);
                    }
                }
            }
        }

        @Override
        public void doAtEndOfTurn(GameData gameData) {
            super.doAtEndOfTurn(gameData);
            Set<FoodItem> toRemove = new HashSet<>();
            for (FoodItem it : trackedFood) {
                if (it.getHolder().getActor() instanceof Player) {
                    Player pl = (Player)(it.getHolder().getActor());
                    if (pl.getNextAction() instanceof ConsumeAction) {
                        ConsumeAction act = (ConsumeAction)(pl.getNextAction());
                        if (act.getConsumable() == it && pl != getActor()) {
                            current++;
                            toRemove.add(it);
                        }
                    }
                }
            }


            trackedFood.removeAll(toRemove);
        }
    }
}
