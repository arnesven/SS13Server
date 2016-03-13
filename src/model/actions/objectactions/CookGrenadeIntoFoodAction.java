package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.items.foods.ExplodingFood;
import model.items.foods.FoodItem;
import model.items.weapons.Grenade;
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
	protected String getVerb() {
		return innerAction.getVerb();
	}
	
	@Override
	public String toString() {
		String inner = innerAction.toString();
		inner = inner.replace("Cook Food", "Cook Grenade Into Food");
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
		performingClient.addItem(food);
		performingClient.addTolastTurnInfo("You cooked the grenade into " + 
						innerAction.getSelectedItem());
		
	}

	@Override
	public void setArguments(List<String> args) {
		innerAction.setArguments(args);
	}

}