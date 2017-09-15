package model.events.damage;

import model.Actor;
import model.GameData;
import model.Target;
import model.items.general.GameItem;
import model.objects.general.BreakableObject;

/**
 * Created by erini02 on 17/04/16.
 */
public abstract class DamagerImpl implements Damager {


    @Override
    public void doDamageOnMe(Target target) {
        if (target instanceof Actor) {
            ((Actor)target).subtractFromHealth(getDamage());
        } else {
            target.addToHealth(-getDamage());
        }
    }

    @Override
    public GameItem getOriginItem() {
        return null;
    }
}
