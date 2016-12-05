package model.modes.goals;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;

/**
 * Created by erini02 on 03/12/16.
 */
public class CollectMoneyTask extends PersonalGoal {

    private int quant;

    public CollectMoneyTask(int i) {
        this.quant = i;
    }

    @Override
    public String getText() {
        return "Collect $$ " + this.quant;
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            MoneyStack m = MoneyStack.getActorsMoney(getBelongsTo());
            return m.getAmount() >= this.quant;
        } catch (NoSuchThingException e) {

        }
        return false;
    }
}
