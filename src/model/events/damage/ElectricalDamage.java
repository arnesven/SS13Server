package model.events.damage;

import sounds.Sound;

/**
 * Created by erini02 on 17/12/16.
 */
public class ElectricalDamage extends DamagerImpl {
    private final double dam;

    public ElectricalDamage(double v) {
        this.dam = v;
    }

    @Override
    public double getDamage() {
        return dam;
    }

    @Override
    public String getText() {
        if (dam >= 1.0) {
            return "You got a severe electric shock!";
        }
        return "You got an electric shock!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Electrocution";
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("http://www.ida.liu.se/~erini02/ss13/electric_shock.ogg");
    }
}
