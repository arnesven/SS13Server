package model.items.foods;

import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.AlterStrength;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InstanceRemover;
import model.events.RemoveInstanceLaterEvent;

public class SpinachSoup extends FoodItem {

	public SpinachSoup() {
		super("Spinach Soup", 1.0);
	}

	@Override
	public double getFireRisk() {
		return 0.2;
	}
	

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		eatenBy.addTolastTurnInfo("You feel stronger.");
		eatenBy.setCharacter(new AlterStrength(eatenBy.getCharacter(), 
				"Spinach Soup", false, 10.0));
		
		gameData.addEvent(new RemoveInstanceLaterEvent(eatenBy, gameData.getRound(), 
				6, new InstanceRemover() {

			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof AlterStrength) {
					return ((CharacterDecorator)ch).getInner();
				} else if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator)ch).getInner());
				}
				throw new NoSuchElementException("Did not find that instance!");
			}
		}));

	}

}