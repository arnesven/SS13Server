package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class ApplePie extends HealingFood {

	public ApplePie(Actor maker) {
		super("Apple Pie", 0.5, maker, 20);
	}

	@Override
	public double getFireRisk() {
		return 0.1;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("applepie", "food.png", 15, 12, this);
    }

    @Override
	public ApplePie clone() {
		return new ApplePie(this.getMaker());
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Ah a lovely, tasty apple pie. Just like your mama would make on summer mornings.";
	}
}
