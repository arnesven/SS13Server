package model.items.weapons;


public class LaserPistol extends AmmoWeapon {

	public LaserPistol() {
		super("Laser pistol", 0.90, 1.0, false, 1.0, 4);
	}

	@Override
	public LaserPistol clone() {
		return new LaserPistol();
	}
	
	@Override
	protected char getIcon() {
		return 'L';
	}
	
}
