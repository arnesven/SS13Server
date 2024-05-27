package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class TornClothes extends GameItem {

	public TornClothes() {
		super("Torn Clothes", 0.5, 1);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("tornclothes", "uniform2.png", 4, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Ripped and broken clothing. Who did this?";
	}

	@Override
	public GameItem clone() {
		return new TornClothes();
	}

}
