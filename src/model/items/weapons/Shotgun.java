package model.items.weapons;


import graphics.Sprite;
import model.Actor;

public class Shotgun extends AmmoWeapon {

	public Shotgun() {
		super("Shotgun", 0.90, 1.0, true, 2.0, 2);
	}
	
	@Override
	public Shotgun clone() {
		return new Shotgun();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("shotgun", "gun.png", 7);
    }
}
