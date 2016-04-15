package model.items.foods;

import model.Actor;
import model.GameData;
import model.characters.decorators.DrunkChecker;
import model.events.DrunkTimerEvent;
/**
 * @author chrho686
 * Abstract base class for all alcoholic beverages
 */
public abstract class Alcohol extends FoodItem {

	private int potency;
	
	public Alcohol(String name, double weight, int potency) {
		super(name, weight);
		this.potency = potency;
	}

	@Override
	public double getFireRisk() {
		return 1.0;
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		gameData.addEvent(new DrunkTimerEvent(eatenBy, potency, new DrunkChecker()));
	}
}
