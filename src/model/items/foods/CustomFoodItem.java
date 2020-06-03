package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class CustomFoodItem extends HealingFood {
    private final Sprite sprite;
    private final Player designer;

    public CustomFoodItem(String dishName, Sprite totalSprite, Player maker) {
        super(dishName, 0.35, maker, 25);
        this.sprite = totalSprite;
        sprite.setObjectRef(this);
        this.designer = maker;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    @Override
    public double getFireRisk() {
        return 0.05;
    }

    @Override
    public CustomFoodItem clone() {
        return new CustomFoodItem(getBaseName(), sprite, designer);
    }

    public CustomFoodItem copyYourself(Actor maker) {
        CustomFoodItem cfi = this.clone();
        cfi.setMaker(maker);
        return cfi;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A custom dish made by " + designer.getBaseName() + ". Enjoy!";
    }

    public Player getDesigner() {
        return designer;
    }
}
