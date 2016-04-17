package model.events.damage;

/**
 * Created by erini02 on 17/04/16.
 */
public class FireDamage extends DamagerImpl {

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "fire";
    }

    @Override
    public String getText() {
        return "The fire burned you!";
    }

    @Override
    public double getDamage() {
        return 0.5;
    }
}
