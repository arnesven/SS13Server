package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class ChefsHat extends HatItem {

	public ChefsHat() {
		super("Chef's Hat", 0.2, 10);
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("chefshat", "hats.png", 14, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("chefshatworn", "head.png", 1, 8, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean permitsOver() {
		return false;
	}

	@Override
	public ChefsHat clone() {
		return new ChefsHat();
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Worn by chefs to make them appear more masterful. " +
				"Some say that the placebo effect of this may be so strong that chefs actually become a bit more skilled in the kitchen.";
	}
}
