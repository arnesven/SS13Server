package model.items.weapons;


public abstract class BluntWeapon extends Weapon {

	public BluntWeapon(String string, double weight, int cost) {
		super(string, 0.65, 1.0, false, weight, cost);
	}

}
