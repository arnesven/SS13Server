package model.items.general;

import model.Actor;
import model.ItemHolder;
import model.Target;
import model.map.Room;

/**
 * Created by erini02 on 20/11/16.
 */
public abstract class ItemStack extends GameItem {

    private final double weightOfOne;
    private final int costOfOne;
    private int amount;

    public ItemStack(String string, double weight, int cost, int startingAmount) {
        super(string, weight, cost);
        amount = startingAmount;
        weightOfOne = weight;
        costOfOne = cost;
    }

    @Override
    public double getWeight() {
        return weightOfOne * getAmount();
    }

    @Override
    public int getCost() {
        return costOfOne * getAmount();
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return getBaseName() + "(" + amount + ")";
    }

    public void combineWith(ItemStack other) {
        this.amount += other.amount;
    }


    public void addTo(int amount) {
        this.amount += amount;
    }

    public boolean subtractFrom(int amount) throws ItemStackDepletedException {
        if (amount > this.amount) {
            return false;
        } else if (amount == this.amount) {
            this.amount = 0;
            throw new ItemStackDepletedException();
        }
        this.amount -= amount;
        return true;
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        combineStacks(to);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void gotAddedToRoom(Actor cameFrom, Room to) {
        combineStacks(to);
    }

    private void combineStacks(ItemHolder to) {
        ItemStack found = null;
        ItemStack garbage = null;
        for (GameItem it : to.getItems()) {
            if (it.getClass() == this.getClass()) {
                if (found == null) {
                    found = (ItemStack) it;
                } else {
                    found.combineWith((ItemStack)it);
                    garbage = (ItemStack) it;
                }
            }
        }
        if (garbage != null) {
            to.getItems().remove(garbage);
        }
    }
}
