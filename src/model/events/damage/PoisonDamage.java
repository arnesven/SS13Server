package model.events.damage;

import model.Target;

/**
 * Created by erini02 on 29/11/16.
 */
public class PoisonDamage extends DamagerImpl {
    private final double potency;

    public PoisonDamage(double v) {
        this.potency = v;
    }


    @Override
    public double getDamage() {
        return potency;
    }

    @Override
    public String getText() {
        return "You don't feel so well.";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Poisoning";
    }
}
