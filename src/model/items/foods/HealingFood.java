package model.items.foods;

import model.Actor;
import model.GameData;

public abstract class HealingFood extends FoodItem {

	public HealingFood(String string, double weight) {
		super(string, weight);
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		eatenBy.getAsTarget().addToHealth(0.5);
		eatenBy.addTolastTurnInfo("Mmm, tastes good!");
	}

}
