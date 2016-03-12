package model.items;

public abstract class BluntWeapon extends Weapon {

	public BluntWeapon(String string, double weight) {
		super(string, 0.65, 0.5, false, weight);
	}

}
