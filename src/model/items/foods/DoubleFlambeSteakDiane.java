package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceChecker;
import model.events.RemoveInstanceLaterEvent;

public class DoubleFlambeSteakDiane extends HealingFood {

	public DoubleFlambeSteakDiane(Actor maker) {
		super("Double Flambé Steak Diane", 0.5, maker, 129);
	}

	@Override
	public double getFireRisk() {
		return 0.90;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("doubleflambesteakdiane", "food.png", 17, 3, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "A high quality dish, which requires to flaming frying pans to prepare.";
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
		return new DoubleFlambeSteakDiane(getMaker());
	}

}
