package model.items.weapons;

import model.Actor;


public class AmmoWeapon extends Weapon {

	protected int shots;

	public AmmoWeapon(String string, double hitChance, double damage,
			boolean bang, double weight, int shots) {
		super(string, hitChance, damage, bang, weight);
		this.shots = shots;
	}
	
	
	@Override
	public String getFullName(Actor whosAsking) {
		return super.getFullName(whosAsking) + "(" + shots + ")";
	}
	
	@Override
	public boolean isAttackSuccessful(boolean reduced) {
		shots--;
		return super.isAttackSuccessful(reduced);
	}
	
	@Override
	public boolean isReadyToUse() {
		return shots > 0;
	}

}
