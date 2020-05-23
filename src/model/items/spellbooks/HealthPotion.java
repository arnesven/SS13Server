package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.foods.FoodItem;

public class HealthPotion extends WizardPotion {
    private static final int REGEN_DURATION = 4;

    public HealthPotion() {
        super("Helath Potion", 295, "red");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("healthpotion", "weapons2.png", 2, 25, this);
    }

    @Override
    protected String getPotionDescription() {
        return "A potion that restores 2.0 health and then gives you a 0.5 health regeneration per turn for " + REGEN_DURATION + " turns.";
    }

    @Override
    public FoodItem clone() {
        return new HealthPotion();
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.addToHealth(2.0);
        eatenBy.setCharacter(new HealthRegenerationDecorator(eatenBy, gameData.getRound(), REGEN_DURATION, 0.5));
    }

}
