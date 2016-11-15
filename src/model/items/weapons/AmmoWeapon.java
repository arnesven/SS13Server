package model.items.weapons;

import model.Actor;


public class AmmoWeapon extends Weapon {

	protected int shots;
	private int maxshots;

	public AmmoWeapon(String string, double hitChance, double damage,
			boolean bang, double weight, int shots, int cost) {
		super(string, hitChance, damage, bang, weight, cost);
		this.shots = shots;
		this.maxshots = shots;
        this.setCriticalChance(0.10);
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

	@Override
	public AmmoWeapon clone() {
		return new AmmoWeapon(this.getBaseName(), super.getHitChance(), 
				this.getDamage(), this.makesBang(), this.getWeight(), maxshots, this.getCost());
	}
	
}
