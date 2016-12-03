package model.modes.objectives;

import model.GameData;
import model.ItemHolder;
import model.Player;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.items.general.MoneyStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 21/11/16.
 */
public class MoneyObjective implements TraitorObjective {

    private static final int TARGET_AMOUNT = 2000;
    private final Player traitor;
    private final GameData gameData;

    public MoneyObjective(GameData gameData, Player traitor) {
        this.traitor = traitor;
        this.gameData = gameData;
    }

    @Override
    public String getText() {
        return "Acquire $$ " + TARGET_AMOUNT + ".";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        MoneyStack money;
        try {
            money = MoneyStack.getActorsMoney(traitor);
        } catch (NoSuchThingException e) {
            return false;
        }
        return money.getAmount() >= TARGET_AMOUNT;
    }

    @Override
    public boolean wasCompleted() {
        return isCompleted(gameData);
    }

    @Override
    public int getPoints() {
        return 500;
    }

    @Override
    public Locatable getLocatable() {
        MoneyStack maxMoney = new MoneyStack(0);
        List<ItemHolder> itemHolders = new ArrayList<>();
        itemHolders.addAll(gameData.getRooms());
        itemHolders.addAll(gameData.getActors());

        boolean found = false;
        for (ItemHolder r : itemHolders) {
            if (r != traitor) {
                for (GameItem it : r.getItems()) {
                    if (it instanceof MoneyStack) {
                        if (((MoneyStack) it).getAmount() > maxMoney.getAmount()) {
                            maxMoney = (MoneyStack) it;
                            found = true;
                        }
                    }
                }
            }
        }
        if (found) {
            return maxMoney;
        }
        return null;
    }
}
