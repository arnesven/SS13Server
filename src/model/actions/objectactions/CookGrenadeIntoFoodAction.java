package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.ExplodableItem;
import model.items.general.GameItem;
import model.items.foods.ExplodingFood;
import model.items.foods.FoodItem;
import model.objects.general.CookOMatic;
import sounds.Sound;
import util.Logger;

public class CookGrenadeIntoFoodAction extends Action {

	private CookFoodAction innerAction;
	private CookOMatic cooker;
	private ExplodableItem explosive = null;

	public CookGrenadeIntoFoodAction(CookOMatic cookOMatic,
			CookFoodAction cookFoodAction) {
		super("Cook Explosives Into Food", SensoryLevel.PHYSICAL_ACTIVITY);
		this.cooker = cookOMatic;
		this.innerAction = cookFoodAction;
	}


	@Override
	public boolean hasRealSound() {
		return innerAction.hasRealSound();
	}

	@Override
	public Sound getRealSound() {
		return innerAction.getRealSound();
	}


	@Override
	protected String getVerb(Actor whosAsking) {
		return innerAction.getVerb(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption inner = innerAction.getOptions(gameData, whosAsking);
		inner.setName("Cook Explosives Into Food");
		return inner;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		for (GameItem gi : performingClient.getItems()) {
			if (gi instanceof ExplodableItem) {
				explosive = (ExplodableItem) gi;
				break;
			}
		}
		if (explosive != null) {
			FoodItem food = new ExplodingFood(innerAction.getSelectedItem(), 
					                          performingClient, explosive, innerAction.getSelectedItem().getCost());
			performingClient.getItems().remove(explosive);
			innerAction.cookAndMaybeSendWithDumbwaiter(gameData, performingClient, food);
			performingClient.addTolastTurnInfo("You cooked the explosive into " +
					innerAction.getSelectedItem().getPublicName(performingClient));
		} else {
			performingClient.addTolastTurnInfo("What? The explosive is gone! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		Logger.log("Setting arguments for inner cooking action");
		innerAction.setArguments(args, p);
	}

}
