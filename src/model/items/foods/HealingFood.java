package model.items.foods;

import model.Actor;
import model.GameData;

public abstract class HealingFood extends FoodItem {

    private Actor maker;

	public HealingFood(String string, double weight, Actor maker) {
        // CAREFUL! maker can be null!
		super(string, weight);
        this.maker = maker;

    }

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        if (eatenBy != maker) {
            eatenBy.getAsTarget().addToHealth(1.0);
            eatenBy.addTolastTurnInfo("Mmm, tastes very good!");
        } else {
            eatenBy.getAsTarget().addToHealth(0.5);
            eatenBy.addTolastTurnInfo("Mmm, tastes good.");
        }

	}

    public Actor getMaker() {
        return maker;
    }
}
