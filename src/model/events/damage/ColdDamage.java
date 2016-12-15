package model.events.damage;

public class ColdDamage extends DamagerImpl {

    private double damage = 0.5;

    public ColdDamage() {

    }

    public ColdDamage(double v) {
        damage = v;
    }

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
		return damage;
	}

	@Override
	public String getName() {
		return "hypothermia";
	}

}
