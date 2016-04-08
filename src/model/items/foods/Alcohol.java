package model.items.foods;

import model.Actor;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DrunkChecker;
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

	// TODO this is chaos, please fix this
	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		
		// if character already is drunk
		if((new DrunkChecker()).checkInstanceOf(eatenBy.getCharacter())) {
			
			System.out.println(eatenBy.getPublicName() + " is already drunk.");
			
			// TODO make this more general, this finds the DrunkDecorator
			GameCharacter ch = eatenBy.getCharacter();
			while (ch instanceof CharacterDecorator) {
				if (ch instanceof DrunkDecorator) {
					((DrunkDecorator) ch).addDrunkness(potency);
					break;
				}
				ch = ((CharacterDecorator)ch).getInner();
			}
		} else {
			DrunkTimerEvent timer = new DrunkTimerEvent(eatenBy, potency);
			gameData.addEvent(timer);
		}
	}
}
