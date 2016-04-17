package model.actions.characteractions;

import model.events.damage.ExplosiveDamage;

public class NuclearExplosiveDamage extends ExplosiveDamage {

	public NuclearExplosiveDamage() {
		super(6.0);
	}
	
	@Override
	public String getName() {
		return "Nuclear explosion";
	}
}
