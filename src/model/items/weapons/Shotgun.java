package model.items.weapons;


public class Shotgun extends AmmoWeapon {

	public Shotgun() {
		super("Shotgun", 0.90, 1.0, true, 2.0, 2);
	}
	
	@Override
	public Shotgun clone() {
		return new Shotgun();
	}
	
	@Override
	protected char getIcon() {
		return 'g';
	}

}
