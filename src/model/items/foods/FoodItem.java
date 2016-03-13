package model.items.foods;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.itemactions.EatAction;
import model.items.GameItem;

public abstract class FoodItem extends GameItem {

	public FoodItem(String string, double weight) {
		super(string, weight);
	}
	
	@Override
	public void addYourActions(GameData gameData, 
			ArrayList<model.actions.Action> at, 
			Player cl) {
		at.add(new EatAction(this));
	}
	
	public abstract double getFireRisk();
	protected abstract void triggerSpecificReaction(Actor eatenBy, GameData gameData);
	
	public void beEaten(Actor eatenBy, GameData gameData) {
		eatenBy.getItems().remove(this);
		triggerSpecificReaction(eatenBy, gameData);
	}


	
}