package model.items.general;

import sounds.Sound;

/**
 * Created by erini02 on 28/04/16.
 */
public abstract class UplinkItem extends GameItem {

    public UplinkItem(String s, double v, int cost) {
        super(s, v, cost);
    }

    @Override
    public Sound getDropSound() {
        return new Sound("multitool_drop");
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("multitool_pickup");
    }
}
