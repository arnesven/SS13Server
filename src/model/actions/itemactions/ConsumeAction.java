package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.foods.FoodItem;
import sounds.Sound;

public class ConsumeAction extends Action implements QuickAction {

	private FoodItem food;

	public ConsumeAction(FoodItem food, Actor performer) {
		super("Consume " + food.getPublicName(performer), SensoryLevel.PHYSICAL_ACTIVITY);
		this.food = food;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "consumed something.";
	}

	@Override
	public boolean hasRealSound() {
		return food.hasRealSound();
	}

	@Override
	public Sound getRealSound() {
		return food.getRealSound();
	}

	@Override
	public ActionOption getOptions(GameData gameData,
			Actor whosAsking) {
		return super.getOptions(gameData, whosAsking);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getItems().contains(food) || performingClient.getPosition().getItems().contains(food)) {
			food.beEaten(performingClient, gameData);			
		} else {
			performingClient.addTolastTurnInfo("What? The " + food.getPublicName(performingClient) + " is missing! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { 	}


    public FoodItem getConsumable() {
        return food;
    }

	@Override
	public void performQuickAction(GameData gameData, Player performer) {
		execute(gameData, performer);
	}

	@Override
	public boolean isValidToExecute(GameData gameData, Player performer) {
		return true;
	}

	@Override
	public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
		return performer.getPosition().getClients();
	}
}
