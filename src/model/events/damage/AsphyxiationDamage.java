package model.events.damage;

import model.Target;
import model.objects.general.GameObject;

public class AsphyxiationDamage extends DamagerImpl {

	private Target target;

	public AsphyxiationDamage(Target t) {
		this.target = t;
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return !(target instanceof GameObject);
	}

	@Override
	public String getText() {
		return "your gasping for air!";
	}

	@Override
	public double getDamage() {
		return 0.5;
	}

	@Override
	public String getName() {
		return "asphyxiation";
	}
}
