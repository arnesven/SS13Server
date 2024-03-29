package model.items.weapons;


import graphics.sprites.Sprite;
import model.Actor;
import sounds.Sound;

public class Shotgun extends SlugthrowerWeapon implements PiercingWeapon {

	public Shotgun() {
		super("Shotgun", 0.90, 1.0, true, 2.0, 2, 175);
	}
	
	@Override
	public Shotgun clone() {
		return new Shotgun();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("shotgun", "gun.png", 7, this);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("shotgunhandheld", "items_righthand.png", 8, 23, this);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("shotgun");
    }
}
