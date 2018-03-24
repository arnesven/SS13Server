package model.events.damage;

public class MeteorDamage extends DamagerImpl {
    private final double dmg;

    public MeteorDamage(double v) {
        this.dmg = v;
    }

    @Override
    public double getDamage() {
        return dmg;
    }

    @Override
    public String getText() {
        return "Meteoric Storm";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Meteor blast";
    }
}
