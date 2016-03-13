package model.items.foods;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.itemactions.EatAction;

public class ApplePie extends FoodItem  {

	public ApplePie() {
		super("Apple Pie", 0.5);
	}

	@Override
	public double getFireRisk() {
		return 0.1;
	}

	
	
	@Override
	public void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		eatenBy.getAsTarget().addToHealth(0.5);
		eatenBy.addTolastTurnInfo("Mmm, tastes good!");
	}

}