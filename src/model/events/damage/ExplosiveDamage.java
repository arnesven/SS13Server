package model.events.damage;

import model.items.foods.ExplodingFood;
import model.items.general.GameItem;

public class ExplosiveDamage extends DamagerImpl {

    private GameItem item = null;
    private double damage;

    public ExplosiveDamage(double d) {
        this.damage = d;
    }

	public ExplosiveDamage(double d, GameItem item) {
        this(d);
        this.item = item;
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
		return "Spontaneous explosion";
	}

    @Override
    public GameItem getOriginItem() {
        return item;
    }
}
