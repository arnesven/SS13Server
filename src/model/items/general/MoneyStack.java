package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.ItemHolder;
import model.Target;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;
import model.map.Room;

import java.util.List;

/**
 * Created by erini02 on 14/11/16.
 */
public class MoneyStack extends GameItem {
    private int amount;
    private static final int[] limits = new int[]{10, 100, 200, 500, 1000, 2500, 5000, 10000};

    public MoneyStack(int startingMoney) {
        super("$$", 0.0, 0);
        amount = startingMoney;
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return  "$$ " + amount;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Money Stack";
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        int i = 0;
        for (int lim : limits) {
            if (amount < lim) {
                return new Sprite("monestack"+amount, "items2.png", 4+i, 3);
            }
            i++;
        }
        i--;
        return new Sprite("monestack"+amount, "items2.png", 4+i, 3);


    }

    @Override
    public GameItem clone() {
        return new MoneyStack(amount);
    }

    public void combineWith(MoneyStack other) {
        this.amount += other.amount;
    }

    public void addTo(int amount) {
        this.amount += amount;
    }

    public boolean subtractFrom(int amount) throws MoneyStackDepletedException {
        if (amount > this.amount) {
            return false;
        } else if (amount == this.amount) {
            this.amount = 0;
            throw new MoneyStackDepletedException();
        }
        this.amount -= amount;
        return true;
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        combineStacks(to);

    }

    @Override
    public ActionOption getActionOptions(Actor whosAsking) {
        ActionOption opt = super.getActionOptions(whosAsking);
        opt.addOption("1");
        opt.addOption("10");
        opt.addOption("50");
        opt.addOption(amount/2 + "");
        opt.addOption(amount + "");
        return opt;
    }



    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static MoneyStack getActorsMoney(Actor whosAsking) throws NoSuchThingException {
        return (MoneyStack)GameItem.getItemFromActor(whosAsking, new MoneyStack(1));
    }

    public class MoneyStackDepletedException extends Exception {

    }

    @Override
    public void transferBetweenActors(Actor from, Actor to, String giveArgs) {
        super.transferBetweenActors(from, to, giveArgs);
        // both actors now got money increased by givers full money total
        int totalAmount = this.getAmount();
        int amountTransferred = Integer.parseInt(giveArgs);
        GameItem copy = this.clone();
        from.getCharacter().giveItem(copy, null);
        try {
            // givers stack reduced by giving amount
            ((MoneyStack) copy).subtractFrom(amountTransferred);
        } catch (MoneyStackDepletedException e) {
            from.getCharacter().getItems().remove(copy);
        }

        for (GameItem it : to.getItems()) {
            if (it instanceof MoneyStack) {
                try {
                    ((MoneyStack) it).subtractFrom(totalAmount - amountTransferred);
                } catch (MoneyStackDepletedException e) {
                    e.printStackTrace(); // should not happen!
                }
                break;
            }
        }
    }

    @Override
    public void gotAddedToRoom(Actor cameFrom, Room to) {
        combineStacks(to);
    }

    private void combineStacks(ItemHolder to) {
        MoneyStack found = null;
        MoneyStack garbage = null;
        for (GameItem it : to.getItems()) {
            if (it instanceof MoneyStack) {
                if (found == null) {
                    found = (MoneyStack) it;
                } else {
                    found.combineWith((MoneyStack)it);
                    garbage = (MoneyStack) it;
                }
            }
        }
        if (garbage != null) {
            to.getItems().remove(garbage);
        }
    }

}