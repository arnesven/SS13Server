package model.events.damage;

public class ExplosiveDamage extends DamagerImpl {

	private double damage;

	public ExplosiveDamage(double d) {
		this.damage = d;
	}

	@Override
	public String getText() {
		return "You were hit by an explosion!";
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
