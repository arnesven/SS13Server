package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

/**
 * Created by erini02 on 01/05/16.
 */
public class SpaceBurger extends JunkHealingFood {
    public SpaceBurger(Actor maker) {
        super("Space Burger", 0.4, maker, 30);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spaceburger", "food.png", 2, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A slightly revolting, albeit filling, fast food meal.";
    }

    @Override
    public double getFireRisk() {
        return 0.10;
    }

    @Override
    public FoodItem clone() {
        return new SpaceBurger(getMaker());
    }
}
