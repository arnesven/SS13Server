package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class Chemicals extends GameItem {

	public Chemicals() {
		super("Chemicals", 1.0);
	}

	@Override
	public Chemicals clone() {
		return new Chemicals();
	}
	
	

	public static boolean hasNChemicals(Actor performingClient, int num) {
		int i = 0;
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof Chemicals) {
				i++;
				if (i == num) {
					return true;
				}
			}
		}
		return false;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("chemicals", "chemical.png", 25);
    }
}
