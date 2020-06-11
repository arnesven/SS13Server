package model.items.general;

import sounds.Sound;

public abstract class PartsItemStack extends ItemStack {
    public PartsItemStack(String string, double weight, int cost, int startingAmount) {
        super(string, weight, cost, startingAmount);
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
