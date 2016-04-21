package model.events.damage;

import model.Target;

/**
 * Created by erini02 on 20/04/16.
 */
public class CorrosiveDamage extends DamagerImpl {
    @Override
    public String getText() {
        return "Corrosive Acid";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Corrosive Acid";
    }

    @Override
    public double getDamage() {
        return 1.0;
    }

}
