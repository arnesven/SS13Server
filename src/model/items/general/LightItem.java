package model.items.general;

/**
 * Created by erini02 on 18/12/16.
 */
public abstract class LightItem extends GameItem {
    public LightItem(String string, double weight, boolean usableFromFloor, int cost) {
        super(string, weight, usableFromFloor, cost);
    }
}
