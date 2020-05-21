package model.objects.plants;

import model.Actor;
import model.GameData;
import model.items.foods.FoodItem;

public abstract class MushroomItem extends FoodItem {
    public MushroomItem(String string, double weight, int cost) {
        super(string, weight, cost);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {

    }
}
