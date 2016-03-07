package model.items;

public class AmmoWeapon extends Weapon {

	private int shots;

	public AmmoWeapon(String string, double hitChance, double damage,
			boolean bang, int shots) {
		super(string, hitChance, damage, bang);
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
