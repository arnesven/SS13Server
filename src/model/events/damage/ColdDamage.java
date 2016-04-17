package model.events.damage;

public class ColdDamage extends DamagerImpl {

	@Override
	public String getText() {
		return "You feel a painful shivering in your body.";
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return true;
	}

	@Override
	public double getDamage() {
		return 0.5;
	}

	@Override
	public String getName() {
		return "hypothermia";
	}

}
