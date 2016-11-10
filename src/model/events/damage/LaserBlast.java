package model.events.damage;

import model.Target;

/**
 * Created by erini02 on 02/11/16.
 */
public class LaserBlast extends DamagerImpl {

    @Override
    public double getDamage() {
        return 1.0;
    }

    @Override
    public String getText() {
        return "You got hit by a laser blast!";
    }


    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Laser Blast";
    }
}
