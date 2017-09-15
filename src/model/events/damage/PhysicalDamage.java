package model.events.damage;

import model.items.general.GameItem;

/**
 * Created by erini02 on 15/09/17.
 */
public class PhysicalDamage extends DamagerImpl {

    private final GameItem orig;

    public PhysicalDamage(GameItem origItem) {
        this.orig = origItem;
    }

    @Override
    public String getText() {
        return "You were banged up!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Physical Damage";
    }

    @Override
    public double getDamage() {
        return 0.5;
    }

    @Override
    public GameItem getOriginItem() {
        return this.orig;
    }


}
