package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.characters.decorators.AlterStrength;
import model.characters.decorators.InstanceChecker;
import model.events.RemoveInstanceLaterEvent;

public class SpinachSoup extends HealingFood {

	public SpinachSoup(Actor maker) {
		super("Spinach Soup", 1.0, maker, 20);
	}

	@Override
	public double getFireRisk() {
		return 0.2;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spinachsoup", "food.png", 19, 3);
    }

    @Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		eatenBy.addTolastTurnInfo("You feel stronger.");
		eatenBy.setCharacter(new AlterStrength(eatenBy.getCharacter(), 
				"Spinach Soup", false, 10.0));
		
		gameData.addEvent(new RemoveInstanceLaterEvent(eatenBy, gameData.getRound(), 
				6, new InstanceChecker() {
					
					@Override
					public boolean checkInstanceOf(GameCharacter ch) {
						// TODO Auto-generated method stub
						return ch instanceof AlterStrength;
					}
				}));

	}

	@Override
	public SpinachSoup clone() {
		return new SpinachSoup(getMaker());
	}

}
