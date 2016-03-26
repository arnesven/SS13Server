package model.events;

public class RadiationDamage implements Damager {

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
