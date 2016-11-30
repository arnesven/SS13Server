package model.events.damage;

/**
 * Created by erini02 on 30/11/16.
 */
public class InternalBleeding extends DamagerImpl {
    private final double magnitude;

    public InternalBleeding(double v) {
        this.magnitude = v;
    }

    @Override
    public double getDamage() {
        return magnitude;
    }

    @Override
    public String getText() {
        return "You've suffered serious internal trauma damage.";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return false;
    }

    @Override
    public String getName() {
        return "Internal Bleeding";
    }
}
