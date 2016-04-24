package model.items.foods;

import graphics.Sprite;
import model.Actor;

public class ApplePie extends HealingFood {

	public ApplePie() {
		super("Apple Pie", 0.5);
	}

	@Override
	public double getFireRisk() {
		return 0.1;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("applepie", "food.png", 0);
    }

    @Override
	public ApplePie clone() {
		return new ApplePie();
	}

}
