package model.events.damage;

import sounds.Sound;

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
        return "You were hit by a severe shock wave!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Meteor blast";
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("meteorimpact");
    }
}
