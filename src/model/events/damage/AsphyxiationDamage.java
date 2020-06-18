package model.events.damage;

import model.Target;
import model.objects.general.GameObject;

public class AsphyxiationDamage extends DamagerImpl {

	private final double damage;
	private Target target;

	public AsphyxiationDamage(Target t, double v) {
		this.target = t;
		this.damage = v;
	}

	public AsphyxiationDamage(Target t) {
		this(t, 0.5);
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return !(target instanceof GameObject);
	}

	@Override
	public String getText() {
		return "You're gasping for air!";
	}

	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public String getName() {
		return "asphyxiation";
	}
}
