package model.items.weapons;


import graphics.sprites.Sprite;
import model.Actor;

public class Shotgun extends AmmoWeapon implements PiercingWeapon {

	public Shotgun() {
		super("Shotgun", 0.90, 1.0, true, 2.0, 2, 175);
	}
	
	@Override
	public Shotgun clone() {
		return new Shotgun();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("shotgun", "gun.png", 7);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("shotgunhandheld", "items_righthand.png", 8, 23);
    }
}
