package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.BananaPeelEvent;

public class Banana extends HealingFood {

	public Banana(Actor maker) {
		super("Banana", 0.3, maker, 3);
	}

	@Override
	public double getFireRisk() {
		return 0.0;
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("banana", "items.png", 62, this);
    }

    @Override
	public Banana clone() {
		return new Banana(getMaker());
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		BananaPeelEvent bpe = new BananaPeelEvent(eatenBy);
		gameData.addEvent(bpe);
		eatenBy.getPosition().addEvent(bpe);
		eatenBy.getPosition().addItem(new BananaPeelItem());
	}

}
