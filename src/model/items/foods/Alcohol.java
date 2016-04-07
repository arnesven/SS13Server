package model.items.foods;

import model.Actor;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DrunkDecorator;
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
		if (isDrunk(eatenBy.getCharacter())) {
			
		} else {
			DrunkTimerEvent timer = new DrunkTimerEvent(eatenBy, potency);
			eatenBy.setCharacter(new DrunkDecorator(eatenBy.getCharacter(), timer));
			gameData.addEvent(timer);
		}
	}
	
	protected Boolean isDrunk(GameCharacter ch) {
		if (ch instanceof DrunkDecorator) {
			return true;
		} else if (ch instanceof CharacterDecorator) {
			return isDrunk((CharacterDecorator)ch);
		}
		return false;
	}
	
}
