package model.actions.objectactions;

import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.characters.GameCharacter;
import model.characters.crew.ChefCharacter;
import model.characters.decorators.InstanceChecker;
import model.events.Event;
import model.items.GameItem;
import model.items.foods.FoodItem;
import model.objects.CookOMatic;

public class CookFoodAction extends Action {

	private CookOMatic cooker;
	private FoodItem selectedItem;

	public CookFoodAction(CookOMatic cookOMatic) {
		super("Cook Food", SensoryLevel.OPERATE_DEVICE);
		this.cooker = cookOMatic;
	}
	
	@Override
	protected String getVerb() {
		return "cooked food";
	}
	
	@Override
	public String toString() {
		String res = "Cook Food{";
		
		for (FoodItem gi : CookOMatic.getCookableFood()) {
			res += gi.getName() + "{}";
		}
		
		return res + "}";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		double factor = 1.0;
		
		if (isAChef(performingClient)) {
			factor = 0.25;
			performingClient.addTolastTurnInfo("You are a master in the kitchen!");
		}
		
		if (MyRandom.nextDouble() > selectedItem.getFireRisk()*factor) {
			performingClient.addItem(selectedItem);
			performingClient.addTolastTurnInfo("You successfully cooked a " + 
												selectedItem.getName());
		} else {
			gameData.getGameMode().addFire(performingClient.getPosition());
			performingClient.addTolastTurnInfo("You accidentally started a fire while cooking!");
		}
		
	}
	
	
	public FoodItem getSelectedItem() {
		return selectedItem;
	}

	private boolean isAChef(Actor performingClient) {
		InstanceChecker chefChecker = new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof ChefCharacter;
			}
		};
		
		return performingClient.getCharacter().checkInstance(chefChecker);
	}

	@Override
	public void setArguments(List<String> args) {
		for (FoodItem it : CookOMatic.getCookableFood()) {
			if (it.getName().equals(args.get(0))) {
				selectedItem = it;
			}
		}
	}

}
