package model.events.damage;

import model.events.damage.ExplosiveDamage;

public class NuclearExplosiveDamage extends ExplosiveDamage {

	public NuclearExplosiveDamage() {
		super(60.0);
	}
	
	@Override
	public String getName() {
		return "Nuclear explosion";
	}
}
