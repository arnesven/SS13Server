package model.items.foods;

import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceRemover;
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
				1, new InstanceRemover() {

			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof AlterMovement) {
					return ((CharacterDecorator)ch).getInner();
				} else if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				throw new NoSuchElementException("Did not find that instance!");
			}
		}));

	}

}
