package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class SliceOfPizza extends HealingFood {
    public SliceOfPizza(Actor maker) {
        super("Slice of Pizza", 0.1, maker, 5);
    }

    @Override
    public double getFireRisk() {
        return 0.05;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("sliceofpizza", "food.png", 2, 14, this);
    }

    @Override
    public FoodItem clone() {
        return new SliceOfPizza(getMaker());
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "One slice of a delicious pizza. Share the pleasure!";
    }
}
