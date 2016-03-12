package model.items.weapons;


public class AmmoWeapon extends Weapon {

	protected int shots;

	public AmmoWeapon(String string, double hitChance, double damage,
			boolean bang, double weight, int shots) {
		super(string, hitChance, damage, bang, weight);
		this.shots = shots;
	}
	
	
	@Override
	public String getName() {
		return super.getName() + "(" + shots + ")";
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
