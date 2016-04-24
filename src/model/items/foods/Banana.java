package model.items.foods;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.events.BananaPeelEvent;

public class Banana extends HealingFood {

	public Banana() {
		super("Banana", 0.3);
	}

	@Override
	public double getFireRisk() {
		return 0.0;
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("banana", "items.png", 62);
    }

    @Override
	public Banana clone() {
		return new Banana();
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		BananaPeelEvent bpe = new BananaPeelEvent(eatenBy);
		gameData.addEvent(bpe);
		eatenBy.getPosition().addEvent(bpe);
	}

}
