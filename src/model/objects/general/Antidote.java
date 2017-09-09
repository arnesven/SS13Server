package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.foods.FoodItem;
import model.items.general.GameItem;

/**
 * Created by erini02 on 09/09/17.
 */
public class Antidote extends FoodItem {
    public Antidote() {
        super("Antidote", 0.2, 60);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    public FoodItem clone() {
        return new Antidote();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("antidote", "chemical.png", 53);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {

    }
}
