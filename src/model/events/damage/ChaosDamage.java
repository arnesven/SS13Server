package model.events.damage;

public class ChaosDamage extends DamagerImpl {

    private final double dmg;

    public ChaosDamage(double dmg) {
        this.dmg = dmg;
    }

    @Override
    public String getText() {
        return "Ouch! You were hit by some strange force!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "magical damage";
    }

    @Override
    public double getDamage() {
        return dmg;
    }
}
