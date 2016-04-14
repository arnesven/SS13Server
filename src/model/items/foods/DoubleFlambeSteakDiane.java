package model.items.foods;

import model.Actor;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceChecker;
import model.events.RemoveInstanceLaterEvent;

public class DoubleFlambeSteakDiane extends FoodItem {

	public DoubleFlambeSteakDiane() {
		super("Double Flambé Steak Diane", 0.5);
	}

	@Override
	public double getFireRisk() {
		return 0.90;
	}

	@Override
	public void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		eatenBy.addTolastTurnInfo("You feel energized!");
		eatenBy.setCharacter(new AlterMovement(eatenBy.getCharacter(), 
				"Steak Diane", false, 3));

		gameData.addEvent(new RemoveInstanceLaterEvent(eatenBy, gameData.getRound(), 
				1, new InstanceChecker() {
					
					@Override
					public boolean checkInstanceOf(GameCharacter ch) {
						return ch instanceof AlterMovement;
					}
				}));

	}

	@Override
	public DoubleFlambeSteakDiane clone() {
		return new DoubleFlambeSteakDiane();
	}

}
