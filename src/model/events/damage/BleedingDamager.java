package model.events.damage;

public class BleedingDamager extends DamagerImpl {
    @Override
    public String getText() {
        return "You are bleeding a lot.";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "exsanguination";
    }

    @Override
    public double getDamage() {
        return 0.25;
    }
}
