package model.items.general;

import sounds.Sound;

public abstract class PartsGameItem extends GameItem {

    public PartsGameItem(String string, double weight, boolean usableFromFloor, int cost) {
        super(string, weight, usableFromFloor, cost);
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("component_pickup");
    }

    @Override
    public Sound getDropSound() {
        return new Sound("component_drop");
    }
}
