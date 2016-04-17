package model.events.damage;

public class RadiationDamage extends DamagerImpl {

	private double damage;

	public RadiationDamage(double damage) {
		this.damage = damage;
	}

	@Override
	public String getText() {
		return "You feel sick...";
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return true;
	}

	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public String getName() {
		return "Acute radiation sickness";
	}

}
