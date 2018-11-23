package model.items.foods;

import model.Actor;
import model.GameData;
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
    public double getFireRisk() {
        return innerItem.getFireRisk();
    }

    @Override
    public FoodItem clone() {
        return new BodyPartFood(bodyPart, innerItem);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.addTolastTurnInfo("Wait a minute... what's this? A " + bodyPart.getEatString() + "?");
    }
}
