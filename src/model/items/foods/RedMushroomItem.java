package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.decorators.PoisonedDecorator;

public class RedMushroomItem extends MushroomItem {
    public RedMushroomItem() {
        super("Red Mushroom", 0.1, 75);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    public FoodItem clone() {
        return new RedMushroomItem();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("redmushroomitem", "harvest.png", 5, this);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.setCharacter(new PoisonedDecorator(eatenBy.getCharacter(), null));
    }
}
