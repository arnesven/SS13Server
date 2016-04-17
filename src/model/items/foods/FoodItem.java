package model.items.foods;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.EatAction;
import model.items.general.GameItem;

public abstract class FoodItem extends GameItem {

	public FoodItem(String string, double weight) {
		super(string, weight);
	}
	
	@Override
	protected char getIcon() {
		return 'F';
	}
	
	@Override
	public void addYourActions(GameData gameData, 
			ArrayList<Action> at,
			Player cl) {
		at.add(new EatAction(this));
	}
	
	public abstract double getFireRisk();
	public abstract FoodItem clone();
	protected abstract void triggerSpecificReaction(Actor eatenBy, GameData gameData);
	
	public void beEaten(Actor eatenBy, GameData gameData) {
		eatenBy.getItems().remove(this);
		triggerSpecificReaction(eatenBy, gameData);
	}

	
}
