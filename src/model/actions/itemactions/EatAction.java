package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.foods.FoodItem;

public class EatAction extends Action {

	private FoodItem food;

	public EatAction(FoodItem food) {
		super("Eat " + food.getBaseName(), SensoryLevel.PHYSICAL_ACTIVITY);
		this.food = food;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "ate some food.";
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, 
			Actor whosAsking) {
		return new ActionOption("Eat " + food.getPublicName(whosAsking));
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getItems().contains(food)) {
			food.beEaten(performingClient, gameData);			
		} else {
			performingClient.addTolastTurnInfo("What? The " + food.getPublicName(performingClient) + " is missing! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { 	}

}
