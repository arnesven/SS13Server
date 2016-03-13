package model.events;

public class ExplosiveDamage implements Damager {

	private double damage;

	public ExplosiveDamage(double d) {
		this.damage = d;
	}

	@Override
	public String getText() {
		return "You exploded!";
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
		return "Explosion";
	}

}
