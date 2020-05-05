package model.items.foods;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.DrunkChecker;
import model.events.DrunkTimerEvent;
/**
 * @author chrho686
 * Abstract base class for all alcoholic beverages
 */
public abstract class Alcohol extends FoodItem {

	private int potency;
	
	public Alcohol(String name, double weight, int potency, int cost) {
		super(name, weight, cost);
		this.potency = potency;
	}

	@Override
	public double getFireRisk() {
		return 1.0;
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.addTolastTurnInfo("You feel drunk - \"hic\".");
		//gameData.addEvent(new DrunkTimerEvent(eatenBy, potency, new DrunkChecker()));
	}

    public int getPotency() {
        return potency;
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "An alcoholic beverage. Ubiquitous on planets and space stations alike.";
	}
}
