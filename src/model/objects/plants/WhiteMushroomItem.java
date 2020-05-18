package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.foods.FoodItem;

class WhiteMushroomItem extends MushroomItem {
    public WhiteMushroomItem() {
        super("White Mushroom", 0.1, 35);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("whitemushroomitem", "harvest.png", 6, this);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    public FoodItem clone() {
        return new WhiteMushroomItem();
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {

    }
}
