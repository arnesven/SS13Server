package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;

public class SpaceCheetos extends HealingFood {
    public SpaceCheetos(Actor maker) {
        super("Space Cheetos", 0.2, maker, 6);
    }

    @Override
    public double getFireRisk() {
        return 0.0;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spacecheetosbag", "food.png", 9, this);
    }

    @Override
    public FoodItem clone() {
        return new SpaceCheetos(getMaker());
    }
}
