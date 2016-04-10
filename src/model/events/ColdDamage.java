package model.events;

public class ColdDamage implements Damager {

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
