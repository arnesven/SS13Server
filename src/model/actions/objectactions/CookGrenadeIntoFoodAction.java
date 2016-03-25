package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.items.Grenade;
import model.items.foods.ExplodingFood;
import model.items.foods.FoodItem;
import model.objects.CookOMatic;

public class CookGrenadeIntoFoodAction extends Action {

	private CookFoodAction innerAction;
	private CookOMatic cooker;
	private Grenade grenade;

	public CookGrenadeIntoFoodAction(CookOMatic cookOMatic,
			CookFoodAction cookFoodAction) {
		super("Cook Grenade Into Food", SensoryLevel.PHYSICAL_ACTIVITY);
		this.cooker = cookOMatic;
		this.innerAction = cookFoodAction;
		
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return innerAction.getVerb(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption inner = innerAction.getOptions(gameData, whosAsking);
		inner.setName("Cook Grenade Into Food");
		return inner;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		for (GameItem gi : performingClient.getItems()) {
			if (gi instanceof Grenade) {
				grenade = (Grenade)gi;
				break;
			}
		}
		FoodItem food = new ExplodingFood(innerAction.getSelectedItem(), 
				performingClient);
		performingClient.getItems().remove(grenade);
		performingClient.addItem(food, cooker);
		performingClient.addTolastTurnInfo("You cooked the grenade into " + 
						innerAction.getSelectedItem().getPublicName(performingClient));
		
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		innerAction.setArguments(args, p);
	}

}
