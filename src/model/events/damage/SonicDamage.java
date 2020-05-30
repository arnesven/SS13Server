package model.events.damage;

public class SonicDamage extends DamagerImpl {
    private final double dmg;

    public SonicDamage(double v) {
        this.dmg = v;
    }

    @Override
    public String getText() {
        return "Ahhhh! A VERY loud noise hurts your ears!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "sonic damage";
    }

    @Override
    public double getDamage() {
        return dmg;
    }
}
