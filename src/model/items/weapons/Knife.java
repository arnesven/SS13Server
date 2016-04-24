package model.items.weapons;


import graphics.Sprite;
import model.Actor;

public class Knife extends Weapon {

	public Knife() {
		super("Knife", 0.75, 1.0, false, 0.2, true);
	}

	@Override
	public Knife clone() {
		return new Knife();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("knife", "kitchen.png", 6);
    }
}
