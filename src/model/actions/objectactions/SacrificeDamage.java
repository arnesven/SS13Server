package model.actions.objectactions;

import model.Target;
import model.events.damage.Damager;
import model.events.damage.DamagerImpl;
import model.items.general.GameItem;

public class SacrificeDamage extends DamagerImpl {
    @Override
    public String getText() {
        return "You were sacrificed to Kali!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Ritual Sacrifice";
    }

    @Override
    public double getDamage() {
        return 99.0;
    }

}
