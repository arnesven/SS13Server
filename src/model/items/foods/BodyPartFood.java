package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.BodyPart;

public class BodyPartFood extends FoodItem {
    private final FoodItem innerItem;
    private final BodyPart bodyPart;

    public BodyPartFood(BodyPart bp, FoodItem inner) {
        super(inner.getBaseName(), inner.getWeight(), inner.getCost());
        innerItem = inner;
        bodyPart = bp;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return innerItem.getSprite(whosAsking);
    }

    @Override
    public double getFireRisk() {
        return innerItem.getFireRisk();
    }

    @Override
    public FoodItem clone() {
        return new BodyPartFood(bodyPart, innerItem);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        innerItem.triggerSpecificReaction(eatenBy, gameData);
        eatenBy.addTolastTurnInfo("Wait a minute... what's this? A " + bodyPart.getEatString() + "?");
    }

    @Override
    public boolean canBeCooked(GameData gameData, Actor performingClient) {
        return innerItem.canBeCooked(gameData, performingClient);
    }


    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return innerItem.getExtraDescriptionStats(gameData, performingClient);
    }


    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return innerItem.getDescription(gameData, performingClient);
    }
}
